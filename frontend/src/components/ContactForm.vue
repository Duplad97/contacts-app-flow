<script setup lang="ts">
import { ref } from 'vue';
import { type IContact } from '@/interfaces';
import IconRemove from './icons/IconRemove.vue';
import TextField from './elements/TextField.vue';
import Button from './elements/Button.vue';
import IconChange from './icons/IconChange.vue';
import Modal from './elements/Modal.vue';
import IconAdd from './icons/IconAdd.vue';
import { API_BASE_URL, DEF_PROFILE_IMAGE } from '@/constants';
import api from '@/config/api';

const props = defineProps<{
    contact?: IContact;
    isVisible: boolean;
    closeModal: Function;
}>();

const loading = ref<boolean>(false);

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
            console.log('Image URL:', previewUrl.value);
        };
        reader.readAsDataURL(file);
    }
}

const deleteImage = () => {
    contactData.value = { ...contactData.value, image: null };
    previewUrl.value = null;
}

const closeModal = () => {
    props.closeModal();
};

const handleValueChange = (field: string, value: any) => {
    contactData.value = { ...contactData.value, [field]: value };
}

const submitForm = async () => {
    loading.value = true;
    const formData = new FormData();
    formData.append("name", contactData.value.name);
    formData.append("email", contactData.value.email);
    formData.append("phone", contactData.value.phone);
    
    if (!contactData.value.image && !imageFile.value) {
        formData.append("image", "");
    }

    if (imageFile.value) {
        formData.append("image", imageFile.value);
    }

    if (props.contact) {
        const response = await api.put(`/${props.contact.id}`, formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        })
    } else {
        const response = await api.post("/", formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        });
    }

    loading.value = false;
    closeModal();
};
</script>

<template>
    <Modal :isVisible="props.isVisible" :title="props.contact ? 'Edit contact' : 'Add contact'" @close="closeModal"
        @submit="submitForm">
        <form @submit.prevent="submitForm">
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

            <TextField id="name" label="Name" :value="contactData.name" type="text" :required="true"
                @update:value="(value) => handleValueChange('name', value)" />

            <TextField id="phone" label="Phone number" :value="contactData.phone" type="text" :required="true"
                @update:value="(value) => handleValueChange('phone', value)" />

            <TextField id="email" label="Email address" :value="contactData.email" type="email" :required="true"
                @update:value="(value) => handleValueChange('email', value)" />
        </form>
    </Modal>
</template>