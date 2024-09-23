<script setup lang="ts">
import { ref } from 'vue';
import IconRemove from './icons/IconRemove.vue';
import TextField from './elements/TextField.vue';
import Button from './elements/Button.vue';
import IconChange from './icons/IconChange.vue';
import Modal from './elements/Modal.vue';
import IconAdd from './icons/IconAdd.vue';
import { API_BASE_URL, DEF_PROFILE_IMAGE } from '@/constants';
import { useContactStore, type IContact } from '@/store/useContactStore';
import { useSnackbarStore } from '@/store/useSnackbarStore';
import Loader from './elements/Loader.vue';

const props = defineProps<{
    contact?: IContact;
    isVisible: boolean;
}>();

const contactStore = useContactStore();
const { addSnackbar } = useSnackbarStore();
const loading = ref<boolean>(false);

const emit = defineEmits(['close']);

const fileInput = ref<HTMLInputElement | null>(null);
const imageFile = ref<File | null>(null);
const previewUrl = ref<string | null>(null);

const contactData = ref<IContact>({
    id: props.contact?.id || -1,
    name: props.contact?.name || '',
    email: props.contact?.email || '',
    phone: props.contact?.phone || '',
    image: props.contact?.image || null,
});

const selectImage = () => {
    fileInput.value?.click();
};

const handleChangeFile = (event: Event) => {
    const target = event.target as HTMLInputElement;
    const file = target.files?.[0];
    if (file) {
        imageFile.value = file;
        const reader = new FileReader();
        reader.onload = (e) => {
            previewUrl.value = e.target?.result as string;
        };
        reader.readAsDataURL(file);
    }
}

const deleteImage = () => {
    contactData.value = { ...contactData.value, image: null };
    previewUrl.value = null;
}

const handleClose = (closeModal: () => void) => {
    closeModal();
    contactData.value = {
        id: props.contact?.id || -1,
        name: props.contact?.name || '',
        email: props.contact?.email || '',
        phone: props.contact?.phone || '',
        image: props.contact?.image || null,
    };
}

const handleValueChange = (field: string, value: any) => {
    contactData.value = { ...contactData.value, [field]: value };
}

const submitForm = async (closeModal: () => void) => {
    loading.value = true;

    if (props.contact) {
        try {
            await contactStore.updateContact(contactData.value, imageFile.value);
            addSnackbar("Contact updated successfully", "success");
        } catch (error) {
            addSnackbar("Error when updating contact", "error");
        }
    } else {
        try {
            await contactStore.addContact(contactData.value, imageFile.value);
            addSnackbar("Contact created successfully", "success");
        } catch (error) {
            addSnackbar("Error when creating contact", "error");
        }
    }

    loading.value = false;
    handleClose(closeModal);
};
</script>

<template>
    <Modal :isVisible="props.isVisible" :title="props.contact ? 'Edit contact' : 'Add contact'" @close="$emit('close')">
        <template #content="{ closeModal }">
            <form @submit.prevent="submitForm(closeModal)">
                <div class="image-edit">
                    <input type="file" accept="image/*" ref="fileInput" @change="handleChangeFile" hidden />
                    <img
                        :src="previewUrl || (contactData.image ? `${API_BASE_URL}/${contactData.image}` : DEF_PROFILE_IMAGE)" />

                    <div class="actions">
                        <Button @click="selectImage"
                            :text="(contactData.image || previewUrl) ? 'Change picture' : 'Add picture'"
                            :icon="(contactData.image || previewUrl) ? IconChange : IconAdd" variation="primary" />
                        <Button v-if="contactData.image || previewUrl" :icon=IconRemove variation="primary"
                            @click="deleteImage" />
                    </div>
                </div>

                <TextField id="name" label="Name" placeholder="John Doe" :value="contactData.name" type="text"
                    :required="true" @update:value="(value) => handleValueChange('name', value)" />

                <TextField id="phone" label="Phone number" placeholder="+36 01 234 5678" :value="contactData.phone"
                    type="phone" :required="false" @update:value="(value) => handleValueChange('phone', value)" />

                <TextField id="email" label="Email address" placeholder="john@doe.com" :value="contactData.email"
                    type="email" :required="false" @update:value="(value) => handleValueChange('email', value)" />

                <div class="footer-actions">
                    <Loader v-if="loading" />
                    <Button @click.prevent="handleClose(closeModal)" text="Cancel" />
                    <Button text="Done" variation="primary" type="submit" />
                </div>
            </form>
        </template>
    </Modal>
</template>