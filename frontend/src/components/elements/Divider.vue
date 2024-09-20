<script setup lang="ts">
const props = defineProps(["position"]);
</script>

<template>
    <div class="divider" :style="{left: props.position}"></div>
</template>