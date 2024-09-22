<script setup lang="ts">
import { onMounted, ref } from 'vue';
import IconMute from './icons/IconMute.vue';
import IconHeadphone from './icons/IconHeadphone.vue';
import IconMore from './icons/IconMore.vue';
import Menu from './elements/Menu.vue';
import IconSettings from './icons/IconSettings.vue';
import IconFavourite from './icons/IconFavourite.vue';
import IconRemove from './icons/IconRemove.vue';
import Button from './elements/Button.vue';
import ContactForm from './ContactForm.vue';
import { API_BASE_URL, DEF_PROFILE_IMAGE } from '@/constants';
import { useContactStore, type IContact } from '@/store/useContactStore';
import { useSnackbarStore } from '@/store/useSnackbarStore';
import DeleteConfirm from './DeleteConfirm.vue';

const props = defineProps<{
    contact: IContact;
}>();

const contactStore = useContactStore();
const { addSnackbar } = useSnackbarStore();

const isMenuOpen = ref(false);
const isFormVisible = ref(false);
const isDelConfVisible = ref(false);
const customButton = ref<any>(null);
const anchorButton = ref<HTMLElement | null>(null);

const toggleMenu = () => {
    isMenuOpen.value = !isMenuOpen.value;
};

const closeMenu = () => {
    isMenuOpen.value = false;
};

const openForm = () => {
    isFormVisible.value = true;
};

const closeForm = () => {
    isFormVisible.value = false;
};

const openDeleteConfirm = () => {
    isDelConfVisible.value = true;
};

const closeDeleteConfirm = () => {
    isDelConfVisible.value = false;
};

const handleDelete = async () => {
    try {
        await contactStore.deleteContact(props.contact.id);
        addSnackbar("Contact deleted successfully", "success");
    } catch (error) {
        addSnackbar("Error when deleting contact", "error");
    }
}

onMounted(() => {
    anchorButton.value = customButton.value?.buttonEl || null;
});
</script>

<template>
    <div class="contact-item">

        <img :src="props.contact?.image ? `${API_BASE_URL}/${props.contact.image}`  : DEF_PROFILE_IMAGE" />

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
            </div>

        </div>

    </div>

    <ContactForm :isVisible="isFormVisible" :contact="props.contact" @close="closeForm" />
    <DeleteConfirm :isVisible="isDelConfVisible" :message="`Are you sure you want to delete ${props.contact.name}?`" @close="closeDeleteConfirm" @confirm="handleDelete" />
</template>