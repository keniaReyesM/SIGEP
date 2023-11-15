package com.accion.ti

import com.accion.ti.dto.excel.FechaExcel
import com.accion.ti.dto.excel.HoraExcel
import com.accion.ti.dto.excel.HorarioExcel
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.enums.DiaEnum
import groovy.sql.Sql
import org.apache.commons.logging.LogFactory
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.joda.time.DateTime
import org.joda.time.Weeks
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.sql.DataSource;
import org.hibernate.transform.Transformers
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

class TareaExcelService extends ExcelService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService
    DataSource dataSource;

    byte[] generarReporteTableroOperacion(def parametros,String path) {
        Sql sql = null;
        try {

            String urlArchivo = "${path}reportes/temporal.xlsx";

            println "urlArchivo -> ${urlArchivo}"
            
            // InputStream archivo = obtenerArchivo(urlArchivo);




            Date fechaInicioJava = utileriaService.parsearDato(parametros.fechaInicio);
            Date fechaFinJava = utileriaService.parsearDato(parametros.fechaFin);

            DateTime fechaInicioJoda = new DateTime(fechaInicioJava);
            DateTime fechaFinJoda = new DateTime(fechaFinJava);

            XSSFWorkbook workbook = new XSSFWorkbook();
            Integer numeroHoja = 0;

            Integer cantidadSemanas = Weeks.weeksBetween(fechaInicioJoda.toLocalDate(), fechaFinJoda.toLocalDate()).getWeeks();
            cantidadSemanas = cantidadSemanas <= 0 ? 1 : cantidadSemanas;


            List<FechaExcel> fechasExcel = new ArrayList<>();
            List<HorarioExcel> horasExcel = new ArrayList<>();
            String color = "#C0E0FB";


            Estado estado = utileriaService.obtenerEstadoActivo();
            Empresa empresa = utileriaService.obtenerEmpresaSesion();
            List<Empleado> empleados = Empleado.findAllByEmpresaAndEstado(empresa, estado);
            sql = new Sql(dataSource);

            //Sección invidual por empleado

            Integer DOMINGO = 7;
            Integer LUNES = 1;
            for (Empleado empleado : empleados) {


                Integer numeroFilaDias = 1;
                Integer numeroFilaFechas = numeroFilaDias + 1;
                Integer numeroFila = numeroFilaFechas + 1;
                Integer numeroSalto = 4;

                Sheet hoja = workbook.createSheet("Hoja "+numeroHoja);
                workbook.setSheetName(workbook.getSheetIndex(hoja), "${numeroHoja + 1}.- ${empleado.getPersona().getNombreCompleto()}");

                fechaInicioJoda = new DateTime(fechaInicioJava);
                fechaFinJoda = new DateTime(fechaFinJava);

                while (fechaInicioJoda.getDayOfWeek() != 1) {
                    fechaInicioJoda = fechaInicioJoda.minusDays(1);
                }
                while (fechaFinJoda.getDayOfWeek() != 7) {
                    fechaFinJoda = fechaFinJoda.plusDays(1);
                }

                for (i in 0..<cantidadSemanas) {

                    Integer minutosAgregar = 15;
                    boolean primerHorario = true;
                    LocalTime horario = LocalTime.of(0, 0)

                    Integer numeroCeldaInicio = 0;
                    Integer numeroCeldaFin = numeroCeldaInicio + 1;

                    boolean esFilaFechas = true;

                    Integer indiceHorario = 0;
                    while (horario != LocalTime.of(0, 0) || primerHorario) {

                        if (esFilaFechas) {

                            Integer margenColumnas = 1;
                            Integer cantidadDiasSemana = 7;
                            Integer saltoDias = 1;
                            Row filaFechas = hoja.getRow(numeroFilaFechas) ?: hoja.createRow(numeroFilaFechas);
                            Row filaDias = hoja.getRow(numeroFilaDias) ?: hoja.createRow(numeroFilaDias);


                            for (fechaInicioJoda; fechaInicioJoda.getDayOfWeek() <= cantidadDiasSemana; fechaInicioJoda = fechaInicioJoda.plusDays(saltoDias)) {
                                Integer numeroColumna = fechaInicioJoda.getDayOfWeek() + margenColumnas

                                Cell celdaFecha = filaFechas.getCell(numeroColumna) ?: filaFechas.createCell(numeroColumna);
                                celdaFecha.setCellValue(utileriaService.formatoFechaExcel(fechaInicioJoda.toDate()))
                               // celdaFecha.setCellStyle(getCellStyleCenter(workbook, color))

                                LocalDate fecha = LocalDate.of(fechaInicioJoda.getYear(), fechaInicioJoda.getMonthOfYear(), fechaInicioJoda.getDayOfMonth())
                                fechasExcel.push(new FechaExcel(hoja: numeroHoja, fila: numeroFilaFechas, columna: numeroColumna, fecha: fecha));

                                Cell celdaDia = filaDias.getCell(numeroColumna) ?: filaDias.createCell(numeroColumna);
                                String nombreDia = DiaEnum.values().find { return it.getNumero() == fechaInicioJoda.getDayOfWeek() }.getValor()
                                celdaDia.setCellValue(nombreDia);
                               // celdaDia.setCellStyle(getCellStyleCenter(workbook, color))

                                if (fechaInicioJoda.getDayOfWeek() == 7) {
                                    break;
                                }


                            }
                            fechaInicioJoda = fechaInicioJoda.plusDays(saltoDias);
                            esFilaFechas = false;
                            continue
                        }
                        primerHorario = false;

                        HoraExcel horaInicio = new HoraExcel(hoja: numeroHoja, fila: numeroFila, columna: numeroCeldaInicio);
                        HoraExcel horaFin = new HoraExcel(hoja: numeroHoja, fila: numeroFila, columna: numeroCeldaFin);

                        Row fila = hoja.getRow(numeroFila) ?: hoja.createRow(numeroFila);
                        numeroFila++;

                        Cell celdaInicio = fila.getCell(numeroCeldaInicio) ?: fila.createCell(numeroCeldaInicio)
                        celdaInicio.setCellValue(horario.toString())
                       // celdaInicio.setCellStyle(getCellStyleCenter(workbook, color))
                        horaInicio.setHora(horario)

                        horario = horario.plusMinutes(minutosAgregar)

                        Cell celdaFin = fila.getCell(numeroCeldaFin) ?: fila.createCell(numeroCeldaFin);
                        celdaFin.setCellValue(horario.toString())
                       // celdaFin.setCellStyle(getCellStyleCenter(workbook, color))
                        horaFin.setHora(horario)

                        horasExcel.push(new HorarioExcel(horaInicio: horaInicio, horaFin: horaFin))

                        List<FechaExcel> fechasCabecera = fechasExcel.findAll { it ->
                            return it.getHoja() == numeroHoja && it.getFila() == numeroFilaFechas;
                        }

                        for (FechaExcel fechaExcel in fechasCabecera) {


                            String tareaSQL = '''
                                SELECT 
                                    CONCAT(tarea.clave, ' - ', tarea.nombre, ' - ', proyecto.nombre, ': ', avance.hora_inicio, ' a ', avance.hora_fin) AS tarea,
                                    estado.color AS color,
                                    avance.hora_inicio AS horaInicio,
                                    avance.hora_fin AS horaFin
                                FROM avance
                                INNER JOIN asignacion_tarea ON asignacion_tarea.id = avance.asignacion_tarea_id
                                INNER JOIN tarea ON tarea.id = asignacion_tarea.tarea_id
                                INNER JOIN estado ON estado.id = tarea.estado_id
                                INNER JOIN proyecto ON proyecto.id = tarea.proyecto_id
                                WHERE asignacion_tarea.empleado_id = :idEmpleado 
                                AND avance.fecha_avance BETWEEN :fechaInicio AND :fechaFin
                                AND (
                                        (avance.hora_inicio = :horaInicio)
                                        OR
                                        (avance.hora_inicio > :horaInicio AND avance.hora_inicio < :horaFin)     
                                        OR
                                         (:horaFin >= avance.hora_inicio AND :horaFin <= avance.hora_fin)                                  
                                    )
                            ''';


                            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

                            def parametrosSQL = [
                                    idEmpleado : empleado.id,
                                    fechaInicio: fechaExcel.getFecha().format(formatoFecha),
                                    fechaFin: fechaExcel.getFecha().format(formatoFecha),
                                    horaInicio: horaInicio.getHora().format(formatoHora),
                                    horaFin: horaFin.getHora().format(formatoHora),
                            ];

                            def tareas = sql.rows(tareaSQL, parametrosSQL);


                            if (tareas.size() > 1) {
                                println "************************"
                                println tareas*.tarea;
                            }

                            String colorTarea = tareas ? tareas.get(0).color : null;
                            if (colorTarea == null) {
                                colorTarea = indiceHorario % 2 ? "F2F2F2" : null;
                            }

                            Cell celdaTarea = fila.getCell(fechaExcel.getColumna()) ?: fila.createCell(fechaExcel.getColumna())
                            String tareasFechaHora = tareas ? tareas*.tarea.join(", ") : "";
                            celdaTarea.setCellValue(tareasFechaHora);
                           // celdaTarea.setCellStyle(getCellStyleCenter(workbook, colorTarea))

                        }

                        indiceHorario++;
                    }

                    numeroFila = numeroFila + numeroSalto;
                    numeroFilaFechas = numeroFila - 1;
                    numeroFilaDias = numeroFilaFechas - 1;

                }

                numeroHoja++;

            }


            //Sección general.

            if (utileriaService.nonEmptyList(empleados)) {

                Long idEmpresa = empresa.id;
                Integer tipoFormatoFecha = 2;
                fechaInicioJoda = new DateTime(fechaInicioJava);
                fechaFinJoda = new DateTime(fechaFinJava);

                Sheet hoja = numeroHoja == 0 ? workbook.getSheetAt(numeroHoja) : workbook.createSheet();
                workbook.setSheetName(workbook.getSheetIndex(hoja), "RESUMEN DE HORAS");

                Integer numeroFilaCategoria = 1;
                Integer numeroFilaTareas = numeroFilaCategoria + 1;
                Integer numeroFilaEmpleado = numeroFilaTareas + 1;
                Integer numeroFilaHoras = numeroFilaTareas + 1;

                Integer numeroCeldaEmpleado = 0;
                Integer numeroCeldaCategoria = numeroCeldaEmpleado + 1;
                Integer numeroCeldaTarea = numeroCeldaEmpleado + 1;
                Integer numeroCeldaHoras = numeroCeldaEmpleado + 1;

                String querySQL = '''
                     SELECT categoria.id AS idCategoria, categoria.nombre AS nombreCategoria, 
                            COUNT(tarea.id) AS cantidadTareas
                     FROM categoria
                     INNER JOIN proyecto ON proyecto.categoria_id = categoria.id
                     INNER JOIN tarea ON tarea.proyecto_id = proyecto.id
                     INNER JOIN estado ON tarea.estado_id = estado.id
                     WHERE  proyecto.empresa_id = :idEmpresa
                          AND ( tarea.fecha_hora_inicio BETWEEN :fechaHoraInicio AND :fechaHoraFin
                            OR
                            tarea.fecha_hora_fin BETWEEN :fechaHoraInicio AND :fechaHoraFin)
                      GROUP BY categoria.id
                      ORDER BY categoria.nombre ASC
                 ''';

                Date fechaInicio = utileriaService.startDay(fechaInicioJoda).toDate();
                Date fechaFin = utileriaService.startDay(fechaFinJoda).toDate();

                def parametrosSQL = [
                        idEmpresa      : idEmpresa,
                        fechaHoraInicio: utileriaService.formatoFechaHora(fechaInicio, tipoFormatoFecha),
                        fechaHoraFin   : utileriaService.formatoFechaHora(fechaFin, tipoFormatoFecha)
                ];

                def categorias = sql.rows(querySQL, parametrosSQL);
                println "categorias ${categorias}"
                boolean primerSeccion = true;
                for (def categoria : categorias) {

                    Long idCategoria = categoria.idCategoria as Long

                    Row fila = hoja.getRow(numeroFilaCategoria) ?: hoja.createRow(numeroFilaCategoria);

                    Cell celdaCategoria = fila.getCell(numeroCeldaCategoria) ?: fila.createCell(numeroCeldaCategoria)
                    celdaCategoria.setCellValue("${categoria.nombreCategoria}: tareas ${categoria.cantidadTareas}")
                    // celdaCategoria.setCellStyle(getCellStyleCenter(workbook))
                    if (categoria.cantidadTareas > 1) {
                        Integer celdasUnidas = (categoria.cantidadTareas - 1);
                        hoja.addMergedRegion(new CellRangeAddress(numeroFilaCategoria, numeroFilaCategoria, numeroCeldaCategoria, (numeroCeldaCategoria + celdasUnidas)));
                        numeroCeldaCategoria = numeroCeldaCategoria + categoria.cantidadTareas;
                    } else {
                        numeroCeldaCategoria++;
                    }


                    querySQL = '''
                        SELECT 
                               tarea.id AS idTarea, 
                               tarea.clave AS claveTarea,
                               tarea.nombre AS nombreTarea, 
                               proyecto.nombre AS nombreProyecto, 
                               proyecto.color AS colorProyecto,
                               estado.color AS colorEstado
                        FROM categoria
                        INNER JOIN proyecto ON proyecto.categoria_id = categoria.id
                        INNER JOIN tarea ON tarea.proyecto_id = proyecto.id
                        INNER JOIN estado ON tarea.estado_id = estado.id
                        WHERE proyecto.empresa_id = :idEmpresa
                             AND categoria.id = :idCategoria
                             AND ( tarea.fecha_hora_inicio BETWEEN :fechaHoraInicio AND :fechaHoraFin
                                OR
                                tarea.fecha_hora_fin BETWEEN :fechaHoraInicio AND :fechaHoraFin)
                        ORDER BY proyecto.nombre ASC
                    ''';
                    parametrosSQL = [
                            idEmpresa      : idEmpresa,
                            idCategoria    : idCategoria,
                            fechaHoraInicio: utileriaService.formatoFechaHora(fechaInicio, tipoFormatoFecha),
                            fechaHoraFin   : utileriaService.formatoFechaHora(fechaFin, tipoFormatoFecha)
                    ];

                    def tareas = sql.rows(querySQL, parametrosSQL);
                    for (def tarea : tareas) {
                        fila = hoja.getRow(numeroFilaTareas) ?: hoja.createRow(numeroFilaTareas);
                        Cell celdaTarea = fila.getCell(numeroCeldaTarea) ?: fila.createCell(numeroCeldaTarea)
                        celdaTarea.setCellValue("${tarea.claveTarea} - ${tarea.nombreTarea} / ${tarea.nombreProyecto}")
                       // celdaTarea.setCellStyle(getCellStyleCenter(workbook, tarea.colorProyecto))
                        numeroCeldaTarea++;
                    }

                    for (Empleado empleado : empleados) {
                        fila = hoja.getRow(numeroFilaEmpleado) ?: hoja.createRow(numeroFilaEmpleado);
                        Cell celdaEmpleado = fila.getCell(numeroCeldaEmpleado) ?: fila.createCell(numeroCeldaEmpleado)
                        celdaEmpleado.setCellValue(empleado.persona.nombreCompleto)
                        //celdaEmpleado.setCellStyle(getCellStyleCenter(workbook))
                        numeroFilaEmpleado++;

                        numeroCeldaHoras = primerSeccion ? (numeroCeldaEmpleado + 1) : numeroCeldaTarea;
                        for (def tarea : tareas) {
                            fila = hoja.getRow(numeroFilaHoras) ?: hoja.createRow(numeroFilaHoras);
                            Cell celdaHoras = fila.getCell(numeroCeldaHoras) ?: fila.createCell(numeroCeldaHoras);
                            querySQL = ''' 
                                             SELECT 
                                                SEC_TO_TIME((SUM(TIMESTAMPDIFF(MINUTE, avance.hora_inicio, avance.hora_fin))) * 60) AS tiempoInvertido
                                            FROM tarea 
                                            INNER JOIN asignacion_tarea ON asignacion_tarea.tarea_id = tarea.id 
                                            INNER JOIN avance ON avance.asignacion_tarea_id = asignacion_tarea.id
                                            WHERE tarea.id = :idTarea
                                                AND  asignacion_tarea.empleado_id = :idEmpleado
                                                AND (avance.fecha_avance BETWEEN :fechaInicio AND :fechaFin)
                                          ''';
                            parametros = [idTarea    : tarea.idTarea,
                                          idEmpleado : empleado.id,
                                          fechaInicio: utileriaService.formatoFecha(fechaInicio),
                                          fechaFin   : utileriaService.formatoFecha(fechaFin)
                            ];
                            def asignacionTarea = sql.firstRow(querySQL, parametros);

                            String tiempoInvertido = ""
                            if(asignacionTarea && asignacionTarea.tiempoInvertido){
                                tiempoInvertido = "${asignacionTarea.tiempoInvertido}";
                            }

                            celdaHoras.setCellValue(tiempoInvertido);
                            //celdaHoras.setCellStyle(getCellStyleCenter(workbook, tarea.colorEstado));
                            numeroCeldaHoras++;
                        }
                        numeroFilaHoras++;
                    }

                    fila = hoja.getRow(numeroFilaEmpleado) ?: hoja.createRow(numeroFilaEmpleado);
                    Cell celdaEmpleado = fila.getCell(numeroCeldaEmpleado) ?: fila.createCell(numeroCeldaEmpleado)
                    celdaEmpleado.setCellValue("TOTAL")
                    // celdaEmpleado.setCellStyle(getCellStyleCenter(workbook))

                    int numeroCeldaTotal = numeroCeldaEmpleado + 1;
                    for (def tarea : tareas) {

                        querySQL = ''' 
                                    SELECT 
                                        SEC_TO_TIME((SUM(TIMESTAMPDIFF(MINUTE, avance.hora_inicio, avance.hora_fin))) * 60) AS tiempoInvertido
                                    FROM tarea 
                                    INNER JOIN asignacion_tarea ON asignacion_tarea.tarea_id = tarea.id 
                                    INNER JOIN avance ON avance.asignacion_tarea_id = asignacion_tarea.id
                                    WHERE tarea.id = :idTarea
                                          AND  asignacion_tarea.estado_id = :idEstado
                                          AND (avance.fecha_avance BETWEEN :fechaInicio AND :fechaFin)
                        ''';
                        parametros = [idTarea    : tarea.idTarea,
                                      idEstado   : estado.id,
                                      fechaInicio: utileriaService.formatoFecha(fechaInicio),
                                      fechaFin   : utileriaService.formatoFecha(fechaFin)
                        ];
                        def asignacionTarea = sql.firstRow(querySQL, parametros);
                        
                        String tiempoInvertido = ""
                        if(asignacionTarea && asignacionTarea.tiempoInvertido){
                            tiempoInvertido = "${asignacionTarea.tiempoInvertido}";
                        }

                        Cell celdaHoras = fila.getCell(numeroCeldaTotal) ?: fila.createCell(numeroCeldaTotal);
                        celdaHoras.setCellValue(tiempoInvertido);
                        // celdaHoras.setCellStyle(getCellStyleCenter(workbook, tarea.colorEstado));
                        numeroCeldaTotal++;

                    }
                    primerSeccion = false;

                }
            }


            //Generación y acomo de reporte.
            autoSizeColumns(workbook);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            byte[] reporte = baos.toByteArray();
            baos.close();
            return reporte;

        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        } finally {
            utileriaService.cerrarConexiones(sql);
        }
    }

}
