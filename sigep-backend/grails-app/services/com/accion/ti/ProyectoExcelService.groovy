package com.accion.ti

import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.enums.CategoriaEnum
import com.accion.ti.enums.TipoEstadoEnum
import com.accion.ti.enums.TipoPrivacidadEnum
import org.apache.commons.logging.LogFactory
import org.springframework.web.multipart.MultipartFile
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO
import org.apache.commons.logging.LogFactory
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook


class ProyectoExcelService extends ExcelService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService
    ProyectoService proyectoService

    void importarProyectos(MultipartFile multipartFile) {
        try {

            def proyectosExcel = leerArchivoProyectos(multipartFile);
            def proyectosImportados = [];

            Date fechaRegistro = new Date();
            String colorPorDefecto = "#FFFFFF";

            for (def proyectoExcel in proyectosExcel) {
                Proyecto.withTransaction { statusTransaccion ->
                    try {


                        Empresa empresa = utileriaService.obtenerEmpresaSesion();
                        Empleado empleado = utileriaService.obtenerEmpleadoSesion();

                        Proyecto proyecto = Proyecto.findByClaveAndEmpresa(proyectoExcel.clave, empresa);

                        if (proyecto != null) {
                            proyectoExcel[ExcelConstants.COLUMNA_NOTIFICACION_IMPORTACION_EXCEL] = ExcelConstants.MENSAJE_ACTUALIZADO;
                        }

                        TipoPrivacidad tipoPrivacidad = TipoPrivacidad.findByNombre(TipoPrivacidadEnum.PRIVADO.getValor());

                        Estado estado = null;
                        String nombreEstado = proyectoExcel.estado as String;
                        if (utileriaService.isEmpty(nombreEstado)) {
                            estado = utileriaService.obtenerEstadoActivo();
                        } else {
                            TipoEstado tipoEstado = TipoEstado.findByNombre(TipoEstadoEnum.GENERAL.getValor());
                            estado = Estado.findByTipoEstadoAndNombre(tipoEstado, nombreEstado)
                            if (estado == null) {
                                throw new ServiceException(messageService.getErrorColumnaValorInvalido(ExcelConstants.PROYECTO_ESTATUS_EXCEL, nombreEstado));
                            }
                        }

                        Categoria categoria = null;
                        String nombreCategoria = proyectoExcel.categoria as String;
                        if (utileriaService.isEmpty(nombreCategoria)) {
                            categoria = Categoria.findByEmpresaAndNombre(empresa, CategoriaEnum.NO_DEFINIDO.getValor());
                        } else {
                            categoria = Categoria.findByEmpresaAndNombre(empresa, nombreCategoria);
                            if (categoria == null) {
                                throw new ServiceException(messageService.getErrorColumnaValorInvalido(ExcelConstants.PROYECTO_CATEGORIA_EXCEL, nombreCategoria));
                            }
                        }

                        proyecto = new Proyecto();
                        proyecto.clave = proyectoExcel.clave;
                        proyecto.nombre = proyectoExcel.nombre;
                        proyecto.descripcion = proyectoExcel.descripcion ?: proyecto.nombre;
                        proyecto.fechaInicio = utileriaService.parsearDato(proyectoExcel.fechaInicio)?: fechaRegistro;
                        proyecto.fechaFin = utileriaService.parsearDato(proyectoExcel.fechaFin);
                        proyecto.categoria = categoria;
                        proyecto.estado = estado;
                        proyecto.color = colorPorDefecto;
                        proyecto.tipoPrivacidad = tipoPrivacidad
                        proyecto.fechaRegistro = fechaRegistro;
                        proyecto.empresa = empresa;
                        proyecto.empleado = empleado;
                        proyecto.save(flush: true, failOnError: true);


                        proyectoExcel.clientes = proyectoExcel.clientes ?: [];
                        for (String clienteExcel : proyectoExcel.clientes) {
                            Cliente cliente = Cliente.findByEmpresaAndRazonSocial(empresa, clienteExcel);
                            if(cliente == null){
                                throw new ServiceException("Cliente no registrado.");
                            }
                            if(ClienteProyecto.findByClienteAndProyecto(cliente, proyecto) == null){
                                new ClienteProyecto(cliente: cliente, proyecto: proyecto).save(flush: true, failOnError: true);
                            }
                        }

                    } catch (Exception e) {
                        statusTransaccion.setRollbackOnly()
                        String error = messageService.getMessageErrors(e);
                        proyectosExcel[ExcelConstants.COLUMNA_ERROR_IMPORTACION_EXCEL] = error;
                        LOGGER.error(error);
                    }
                }

                proyectosImportados.push(proyectosExcel)
            }
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    def leerArchivoProyectos(MultipartFile multipartFile) throws ServiceException {
        def objetosExcel = leerArchivo(multipartFile, 0, 0);
        return objetosExcel.collect { objetoExcel ->
            String clientesString = objetoExcel[ExcelConstants.PROYECTO_CLIENTES_EXCEL] ?: null;
            List<String> clientes = new ArrayList<>();
            if (clientesString) {
                clientes = clientesString.split(ExcelConstants.SEPARADOR_EXCEL).toList()
            }
            return [
                    categoria  : objetoExcel[ExcelConstants.PROYECTO_CATEGORIA_EXCEL],
                    clave      : objetoExcel[ExcelConstants.PROYECTO_CLAVE_EXCEL],
                    nombre     : objetoExcel[ExcelConstants.PROYECTO_NOMBRE_EXCEL],
                    descripcion: objetoExcel[ExcelConstants.PROYECTO_DESCRIPCION_EXCEL],
                    fechaInicio: objetoExcel[ExcelConstants.PROYECTO_FECHA_INICIO_EXCEL],
                    fechaFin   : objetoExcel[ExcelConstants.PROYECTO_FECHA_FIN_EXCEL],
                    estado     : objetoExcel[ExcelConstants.PROYECTO_ESTATUS_EXCEL],
                    clientes   : clientes
            ];
        }
    }


    byte[] generarReporteProductividadEmpleado(Long idEmpleado, String path) {
        try {

            String urlArchivo = "${path}reportes/temporal.xlsx";
            println "urlArchivo -> ${urlArchivo}"

            PaginacionDTO  paginacionDTO  = new PaginacionDTO(maximoResultados: null, cantidadOmitir: null);
            
            RespuestaListaDTO<Map> listaDTO = proyectoService.listarPaginadoProductividadEmpleado(paginacionDTO, idEmpleado);
            List<Map> elementos = listaDTO.getElementos();

            def totalHorasTrabajadas = proyectoService.conteoHorasTrabajadas(idEmpleado);

            Empleado empleado = Empleado.findById(idEmpleado)
            
            String nombreEmpleado = "${empleado.getPersona().getNombreCompleto()}";

            XSSFWorkbook workbook = new XSSFWorkbook();
            Integer numeroHoja = 0;
            Integer numeroFila = 1;
            Integer numeroColumna = 1;
            Integer indice = 1;


            Sheet hoja = workbook.createSheet("Hoja ${numeroHoja}");
            workbook.setSheetName(workbook.getSheetIndex(hoja), nombreEmpleado);


            Row fila = hoja.getRow(numeroFila) ?: hoja.createRow(numeroFila);
            Cell celda = fila.getCell(numeroColumna+1) ?: fila.createCell(numeroColumna+1);
            celda.setCellValue("EMPLEADO:");

            celda = fila.getCell(numeroColumna+2) ?: fila.createCell(numeroColumna+2);
            celda.setCellValue(nombreEmpleado);


            numeroFila++;

            def cabeceras = ["No.", "Clave","Nombre", "Estado", "Horas trabajadas"];
            fila = hoja.getRow(numeroFila) ?: hoja.createRow(numeroFila);

            for (cabecera in cabeceras) {
                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue(cabecera);
                numeroColumna++;
            }


            numeroFila++;
            numeroColumna=1;
            for(def elemento : elementos){
                fila = hoja.getRow(numeroFila) ?: hoja.createRow(numeroFila);

                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue("${indice}");
                numeroColumna++;


                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue(elemento.clave);
                numeroColumna++;


                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue(elemento.nombre);
                numeroColumna++;

                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue(elemento.estado);
                numeroColumna++;


                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue(elemento.horasTrabajadas);
                numeroColumna=1;


                numeroFila++;
                indice++;

            }


            fila = hoja.getRow(numeroFila) ?: hoja.createRow(numeroFila);
            numeroColumna=cabeceras.size()-1;
            celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
            celda.setCellValue("TOTAL:");
            
            numeroColumna=cabeceras.size();
            celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
            celda.setCellValue(totalHorasTrabajadas);


            //Generación y acodomo de reporte.
            autoSizeColumns(workbook);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            byte[] reporte = baos.toByteArray();
            baos.close();
            return reporte;

        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    byte[] generarReporteProductividadProyecto(Long idProyecto, String path) {
        try {

            String urlArchivo = "${path}reportes/temporal.xlsx";
            println "urlArchivo -> ${urlArchivo}"

            PaginacionDTO  paginacionDTO  = new PaginacionDTO(maximoResultados: null, cantidadOmitir: null);
            
            RespuestaListaDTO<Map> listaDTO = proyectoService.listarPaginadoProductividadProyecto(paginacionDTO, idProyecto);
            List<Map> elementos = listaDTO.getElementos();

            def proyecto = proyectoService.obtenerInformacionGeneral(idProyecto);

            def totalHorasTrabajadas = proyecto.horasTrabajadas

            
            String nombreProyecto = "${proyecto.clave} - ${proyecto.nombre}";

            XSSFWorkbook workbook = new XSSFWorkbook();
            Integer numeroHoja = 0;
            Integer numeroFila = 1;
            Integer numeroColumna = 1;
            Integer indice = 1;


            Sheet hoja = workbook.createSheet("Hoja ${numeroHoja}");
            workbook.setSheetName(workbook.getSheetIndex(hoja), nombreProyecto);


            Row fila = hoja.getRow(numeroFila) ?: hoja.createRow(numeroFila);
            Cell celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
            celda.setCellValue("PROYECTO:");

            celda = fila.getCell(numeroColumna+1) ?: fila.createCell(numeroColumna+1);
            celda.setCellValue(nombreProyecto);


            numeroFila++;

            def cabeceras = ["No.", "Empleado", "Horas trabajadas"];
            fila = hoja.getRow(numeroFila) ?: hoja.createRow(numeroFila);

            for (cabecera in cabeceras) {
                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue(cabecera);
                numeroColumna++;
            }


            numeroFila++;
            numeroColumna=1;
            for(def elemento : elementos){
                fila = hoja.getRow(numeroFila) ?: hoja.createRow(numeroFila);

                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue("${indice}");
                numeroColumna++;


                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue(elemento.nombreCompleto);
                numeroColumna++;


                celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
                celda.setCellValue(elemento.horasTrabajadas);
                numeroColumna=1;


                numeroFila++;
                indice++;

            }


            fila = hoja.getRow(numeroFila) ?: hoja.createRow(numeroFila);
            numeroColumna=cabeceras.size()-1;
            celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
            celda.setCellValue("TOTAL:");
            
            numeroColumna=cabeceras.size();
            celda = fila.getCell(numeroColumna) ?: fila.createCell(numeroColumna);
            celda.setCellValue(totalHorasTrabajadas);


            //Generación y acodomo de reporte.
            autoSizeColumns(workbook);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            byte[] reporte = baos.toByteArray();
            baos.close();
            return reporte;

        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

}
