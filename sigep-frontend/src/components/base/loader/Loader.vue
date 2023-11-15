<template>
    <div>

        <loader v-if="loader" object="#212121" color1="#ffffff" color2="#17fd3d" size="5" speed="2" bg="#c2c2c2" objectbg="#d1d1d1"
            opacity="19" disableScrolling="false" name="dots"></loader>
    </div>
</template>
<script>
export default {
    props: {
        loader: {
            type: Boolean,
            default: true,
        }
    }
}
</script>