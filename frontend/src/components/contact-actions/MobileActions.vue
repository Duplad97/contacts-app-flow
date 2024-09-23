<script setup lang="ts">
import IconMute from '../icons/IconMute.vue';
import IconHeadphone from '../icons/IconHeadphone.vue';
import IconMoreVert from '../icons/IconMoreVert.vue';
import Menu from '../elements/Menu.vue';
import IconSettings from '../icons/IconSettings.vue';
import IconFavourite from '../icons/IconFavourite.vue';
import IconRemove from '../icons/IconRemove.vue';
import Button from '../elements/Button.vue';
import { onMounted, ref } from 'vue';

const isMenuOpen = ref(false);
const customButton = ref<any>(null);
const anchorButton = ref<HTMLElement | null>(null);

const emit = defineEmits(["openForm", "openDeleteConfirm"]);

const toggleMenu = () => {
    isMenuOpen.value = !isMenuOpen.value;
};

const closeMenu = () => {
    isMenuOpen.value = false;
};

const openForm = () => {
    emit("openForm");
};

const openDeleteConfirm = () => {
    emit("openDeleteConfirm");
};

onMounted(() => {
    anchorButton.value = customButton.value?.buttonEl || null;
});
</script>

<template>
    <Button :icon="IconMoreVert" class="icon-button" v-on="$attrs" @click="toggleMenu" ref="customButton" />
    <Menu v-if="anchorButton" :isOpen="isMenuOpen" :anchorEl="anchorButton" @close="closeMenu">
        <li @click="openForm">
            <IconMute /> Unmute notifications
        </li>
        <li @click="openForm">
            <IconHeadphone /> Mute sound
        </li>
        <li @click="openForm">
            <IconSettings /> Edit
        </li>
        <li>
            <IconFavourite /> Favourite
        </li>
        <li @click="openDeleteConfirm">
            <IconRemove /> Remove
        </li>
    </Menu>
</template>