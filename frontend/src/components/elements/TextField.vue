<script setup lang="ts">
    const props = defineProps({
        value: String,
        required: Boolean,
        id: String,
        label: String,
        type: String
    })
    const emit = defineEmits(['update:value']);

    const handleChange = (event: Event) => {
        const target = event.target as HTMLInputElement;
        emit('update:value', target.value);
    }
</script>

<template>
    <div class="text-field">
        <label :for="props.id">{{ props.label }}</label>
        <input @change="handleChange" v-model="props.value" :id="props.id" :type="props.type" :required=props.required />
    </div>
</template>