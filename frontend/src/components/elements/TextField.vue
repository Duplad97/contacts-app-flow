<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps({
    value: String,
    required: Boolean,
    id: String,
    label: String,
    type: String,
    placeholder: String,
})
const emit = defineEmits(['update:value']);

const handleChange = (event: Event) => {
    const value = (event.target as HTMLInputElement).value;
    if (props.type === "phone") {
        const trimmedValue = value.replace(/\s+/g, '');
        const maxLength = trimmedValue.startsWith('+') ? 12 : 11;
        if (trimmedValue.length <= maxLength) {
            emit('update:value', formatPhone(value));
        }
    } else {
        emit('update:value', value);
    }
}

const onKeyDown = (event: KeyboardEvent) => {
    if (props.type === 'phone') {
        const currentValue = (event.target as HTMLInputElement).value;
        const trimmedValue = currentValue.replace(/\s+/g, '');
        const maxLength = trimmedValue.startsWith('+') ? 12 : 11;

        const isValidKey = /[0-9+]/.test(event.key) ||
            ['Backspace', 'Delete', 'ArrowLeft', 'ArrowRight'].includes(event.key);

        if (!isValidKey || (trimmedValue.length >= maxLength && event.key !== 'Backspace' && event.key !== 'Delete')) {
            event.preventDefault();
        }
    }
};

const formatPhone = (number: string) => {
    const digits = number.replace(/(?!\+)\D/g, '');

    if (digits.startsWith('+')) {
        const cleanDigits = digits.replace(/\++/g, '+').replace(/^\+/g, '');
        return `+${cleanDigits.slice(0, 2)} ${cleanDigits.slice(2, 4)} ${cleanDigits.slice(4, 7)} ${cleanDigits.slice(7)}`;
    } else if (digits.startsWith('0') && digits.length <= 11) {
        return `${digits.slice(0, 2)} ${digits.slice(2, 4)} ${digits.slice(4, 7)} ${digits.slice(7)}`;
    } else if (digits.length > 2) {
        return `+${digits.slice(0, 2)} ${digits.slice(2, 4)} ${digits.slice(4, 7)} ${digits.slice(7)}`;
    }
    return number;
};

</script>

<template>
    <div class="text-field">
        <label :for="props.id">{{ props.label }}</label>
        <input @input="handleChange" :placeholder="props.placeholder" v-model="props.value" :id="props.id"
            :type="props.type" :required=props.required @keydown="onKeyDown" />
    </div>
</template>