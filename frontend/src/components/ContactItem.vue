<template>
    <div class="contact-item">

        <img :src="props.contact?.image" />
        <div class="info">
            <h3>{{ props.contact?.name }}</h3>
            <p>{{ props.contact?.phone }}</p>
        </div>
        <div class="actions">
            <Button :icon=IconMute />
            <Button :icon=IconHeadphone />
            <div class="menu-container">
                <Button :icon="IconMore" v-on="$attrs" @click="toggleMenu" ref="customButton" class="icon-button" />
                <Menu v-if="anchorButton" :isOpen="isMenuOpen" :anchorEl="anchorButton" @close="closeMenu"
                    @mouseleave="closeMenu">
                    <li @click="openModal">
                        <IconSettings /> Edit
                    </li>
                    <li>
                        <IconFavourite /> Favourite
                    </li>
                    <li>
                        <IconRemove /> Remove
                    </li>
                </Menu>
            </div>
        </div>
    </div>
    <ContactForm :isVisible="isModalVisible" :contact="props.contact" :closeModal="closeModal" />
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { type IContact } from '@/interfaces';
import IconMute from './icons/IconMute.vue';
import IconHeadphone from './icons/IconHeadphone.vue';
import IconMore from './icons/IconMore.vue';
import Menu from './elements/Menu.vue';
import IconSettings from './icons/IconSettings.vue';
import IconFavourite from './icons/IconFavourite.vue';
import IconRemove from './icons/IconRemove.vue';
import Button from './elements/Button.vue';
import ContactForm from './ContactForm.vue';

const props = defineProps<{
    contact: IContact;
}>();

const isMenuOpen = ref(false);
const customButton = ref<any>(null);
const anchorButton = ref<HTMLElement | null>(null);

const toggleMenu = () => {
    isMenuOpen.value = !isMenuOpen.value;
};

const closeMenu = () => {
    isMenuOpen.value = false;
};

const isModalVisible = ref(false);

const openModal = () => {
    isModalVisible.value = true;
};

const closeModal = () => {
    isModalVisible.value = false;
};

onMounted(() => {
    anchorButton.value = customButton.value?.buttonEl || null;
});
</script>