<script setup lang="ts">
import { ref, defineEmits } from 'vue';

const props = defineProps<{
    text?: String,
    type?: "button" | "submit" | "reset",
    variation?: String,
    icon?: any,
    rounded?: boolean,
}>();

const className = (props.rounded ? "btn rounded" : "btn") + (props.variation ? ` ${props.variation}`  : "");

const emit = defineEmits(['click']);
const buttonEl = ref<HTMLElement | null>(null);

const handleClick = () => {
    emit('click');
};

defineExpose({ buttonEl });
</script>

<template>
    <button :class="className" ref="buttonEl" @click="handleClick" :type="props.type || 'button'">
        <component v-if="props.icon" class="icon" :is="props.icon" />
        <span v-if="props.text">{{ props.text }}</span>
    </button>
</template>