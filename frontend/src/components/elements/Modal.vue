<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
    isVisible: Boolean,
    title: String,
});

const emit = defineEmits(['close', 'submit']);

const closeModal = () => {
    emit('close');
};

</script>

<template>
    <div v-if="props.isVisible" class="modal-backdrop" @click="closeModal">
        <div class="modal-content" @click.stop>
            <div class="modal-header">
                <h2>{{ props.title }}</h2>
            </div>
            <div class="modal-body">
                <slot></slot>
            </div>
        </div>
    </div>
</template>