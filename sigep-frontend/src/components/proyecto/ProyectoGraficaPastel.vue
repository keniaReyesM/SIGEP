<script>
import { Pie } from 'vue-chartjs';
import ProyectoService from "@/core/services/ProyectoService";

export default {

    extends: Pie,
    props: {
        idProyecto: Number
    },
    data() {
        return {
            chartData: {
                labels: [],
                datasets: [{
                    borderWidth: 1,
                    borderColor: [],
                    backgroundColor: [],
                    data: []
                }]
            },
            options: {
                legend: {
                    display: true
                },
                responsive: true,
                maintainAspectRatio: false
            }
        }
    },
    mounted() {
        
        ProyectoService.obtenerInformacionGraficaCircular(this.idProyecto).then((response) => {

            let estados = response.data;

            this.chartData.labels = estados.map((item) => {
                return item.estado.nombre;
            });

            this.chartData.datasets[0].borderColor = estados.map((item) => {
                return item.estado.color;
            });

            this.chartData.datasets[0].backgroundColor = estados.map((item) => {
                return item.estado.color;
            });

            this.chartData.datasets[0].data = estados.map((item) => {
                return item.cantidad;
            });

            this.renderChart(this.chartData, this.options);


        }).catch(this.$utileria.errorhttp);


    }
}
</script>