<script setup lang="ts">
import Button from './elements/Button.vue';
import IconSettings from './icons/IconSettings.vue';
import IconAdd from './icons/IconAdd.vue';
import ContactForm from './ContactForm.vue';
import { ref } from 'vue';

const isModalVisible = ref(false);

const openModal = () => {
    isModalVisible.value = true;
};

const closeModal = () => {
    isModalVisible.value = false;
};
</script>

<template>
    <div class="contacts-header">
        <h1>Contacts</h1>

        <div class="actions">
            
             <Button :icon=IconSettings />
            <img class="icon-button" src="../assets/images/def_profile_pic.png" />
            <Button text="Add new" type="primary" :icon=IconAdd :rounded=true @click="openModal" />
        </div>
    </div>
    <ContactForm :isVisible="isModalVisible" :closeModal="closeModal" />
</template>