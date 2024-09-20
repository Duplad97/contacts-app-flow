<script setup lang="ts">
    const props = defineProps({
        value: String,
        required: Boolean,
        id: String,
        label: String,
        type: String
    })
</script>

<template>
    <div class="text-field">
        <label :for="props.id">{{ props.label }}</label>
        <input v-model="props.value" :id="props.id" :type="props.type" :required=props.required />
    </div>
</template>