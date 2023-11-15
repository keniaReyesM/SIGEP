<template>
    <strong style="font-style: italic;">{{ ((indice + 1) + (paginaActual * elementosPorPagina) - elementosPorPagina) }}</strong>
</template>

<script>
export default {
    name: "ColumnaIndice",
    props: {
        indice: Number,
        elementosPorPagina: Number,
        paginaActual: Number
    },
}
</script>

<style>

</style>