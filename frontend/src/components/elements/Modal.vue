<template>
    <div v-if="isVisible" class="modal-backdrop" @click="closeModal">
        <div class="modal-content" @click.stop>
            <div class="modal-header">
                <h2>{{ title }}</h2>
            </div>
            <div class="modal-body">
                <slot></slot>
            </div>
            <footer class="modal-footer">
                <Button @click="closeModal" text="Cancel" />
                <Button @click="handleSubmit" text="Done" variation="primary" type="submit" />
            </footer>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';
import Button from './Button.vue';

const props = defineProps({
    isVisible: Boolean,
    title: String,
});

const emit = defineEmits(['close', 'submit']);

const closeModal = () => {
    emit('close');
};

const handleSubmit = () => {
    emit('submit');
};
</script>