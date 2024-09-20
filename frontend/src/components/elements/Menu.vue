<template>
    <transition name="fade">
        <div v-if="isOpen" class="menu" ref="menu" :style="menuStyle" @mouseleave="closeMenu" @keydown.esc="closeMenu">
            <ul>
                <slot />
            </ul>
        </div>
    </transition>
</template>

<script lang="ts">
import { defineComponent, ref, watch, onMounted, onBeforeUnmount } from 'vue';

export default defineComponent({
    props: {
        isOpen: {
            type: Boolean,
            required: true,
        },
        anchorEl: {
            type: Object,
            required: true,
        },
    },
    emits: ['close', 'select'],
    setup(props, { emit }) {
        const menu = ref<HTMLElement | null>(null);

        const menuStyle = ref({ top: '40px', left: '0px' });

        const calculatePosition = () => {
            if (props.anchorEl && menu.value) {
                const anchorRect = props.anchorEl.getBoundingClientRect();
                const menuRect = menu.value.getBoundingClientRect();
                menuStyle.value = {
                    top: `${anchorRect.bottom + window.scrollY}px`,
                    left: `${anchorRect.left + window.scrollX}px`,
                };
            }
        };

        const closeMenu = () => {
            emit('close');
        };

        const handleClickOutside = (event: MouseEvent) => {
            if (menu.value && !menu.value.contains(event.target as Node) && props.anchorEl !== event.target) {
                closeMenu();
            }
        };

        watch(
            () => props.isOpen,
            (newVal) => {
                if (newVal) {
                    calculatePosition();
                    document.addEventListener('click', handleClickOutside);
                } else {
                    document.removeEventListener('click', handleClickOutside);
                }
            }
        );

        onBeforeUnmount(() => {
            document.removeEventListener('click', handleClickOutside);
        });

        return {
            menu,
            menuStyle,
            closeMenu,
        };
    },
});
</script>