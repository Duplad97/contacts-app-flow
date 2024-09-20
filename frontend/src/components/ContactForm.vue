<script setup lang="ts">
import { ref } from 'vue';
import { type IContact } from '@/interfaces';
import IconRemove from './icons/IconRemove.vue';
import TextField from './elements/TextField.vue';
import Button from './elements/Button.vue';
import IconChange from './icons/IconChange.vue';
import Modal from './elements/Modal.vue';
import IconAdd from './icons/IconAdd.vue';

const props = defineProps<{
    contact?: IContact;
    isVisible: boolean;
    closeModal: Function;
}>();

const formData = ref({
    name: props.contact?.name || '',
    email: props.contact?.email || '',
    phone: props.contact?.phone || '',
});

const closeModal = () => {
    props.closeModal();
};

const submitForm = () => {
    console.log('Form submitted:', formData.value);
    closeModal();
};
</script>

<template>
    <Modal :isVisible="props.isVisible" :title="props.contact ? 'Edit contact' : 'Add contact'" @close="closeModal" @submit="submitForm">
        <form @submit.prevent="submitForm">
            <div class="image-edit">
                <img :src="props.contact?.image || 'src/assets/images/contact-image.png'" />
                <Button :text="props.contact?.image ? 'Change picture' : 'Add picture'" :icon="props.contact?.image ? IconChange : IconAdd " type="primary" />
                <Button v-if="props.contact?.image" :icon=IconRemove type="primary" />
            </div>

            <TextField id="name" label="Name" :value="formData.name" type="text" :required="true" />

            <TextField id="phone" label="Phone number" :value="formData.phone" type="text" :required="true" />

            <TextField id="email" label="Email address" :value="formData.email" type="email" :required="true" />
        </form>
    </Modal>
</template>