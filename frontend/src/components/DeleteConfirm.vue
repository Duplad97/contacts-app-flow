<script setup lang="ts">
import Button from './elements/Button.vue';
import Modal from './elements/Modal.vue';

const props = defineProps<{
    message: string;
    isVisible: boolean;
}>();

const emit = defineEmits(["close", "confirm"]);

const handleClose = (closeModal: () => void) => {
    closeModal();
};

const handleConfirm = () => {
    emit("confirm");
}
</script>

<template>
    <Modal :isVisible="isVisible" title="Confirm delete" @close="$emit('close')">
        <template #content="{ closeModal }">
            <p class="confirm-message">{{ props.message }}</p>
            <div class="footer-actions">
                <Button @click.prevent="handleClose(closeModal)" text="Cancel" />
                <Button @click="handleConfirm" text="Confirm" variation="primary" />
            </div>
        </template>
    </Modal>
</template>