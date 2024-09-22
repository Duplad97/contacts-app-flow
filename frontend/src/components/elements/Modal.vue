<script setup lang="ts">
import { defineProps, defineEmits, watch, ref } from 'vue';

const props = defineProps({
    isVisible: Boolean,
    title: String,
});

const emit = defineEmits(['close']);

const closeModal = () => {
    isContentVisible.value = false;
    setTimeout(() => {
        emit('close');
    }, 300)
};

const handleBackdropClick = () => {
    closeModal();
}

const isContentVisible = ref(false);

watch(() => props.isVisible, (newValue: any) => {
    if (newValue) {
        isContentVisible.value = true;
    } else {
        isContentVisible.value = false;
    }
});

const beforeEnter = (el: any) => {
    el.style.transform = 'translateY(100%)';
    el.style.opacity = '0';
};

const enter = (el: any, done: () => void) => {
    requestAnimationFrame(() => {
        el.offsetHeight; // Trigger reflow
        el.style.transition = 'transform 0.3s ease, opacity 0.3s ease';
        el.style.transform = 'translateY(0)';
        el.style.opacity = '1';
        done();
    });
};

const leave = (el: any, done: () => void) => {
    el.style.transition = 'transform 0.3s ease, opacity 0.3s ease';
    el.style.transform = 'translateY(100%)';
    el.style.opacity = '0';

    setTimeout(() => {
        done();
    }, 300);
};
</script>

<template>
    <div v-if="isVisible" class="modal-backdrop" @click="handleBackdropClick">
        <transition name="slide-up" appear @before-enter="beforeEnter" @enter="enter" @leave="leave">
            <div class="modal-content" @click.stop v-if="isContentVisible">
                <div class="modal-header">
                    <h2>{{ title }}</h2>
                </div>
                <div class="modal-body">
                    <slot name="content" :closeModal="closeModal"></slot>
                </div>
            </div>
        </transition>
    </div>
</template>