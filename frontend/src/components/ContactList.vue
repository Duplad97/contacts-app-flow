<script setup lang="ts">
import ContactItem from './ContactItem.vue';
import { onMounted, ref } from 'vue';
import Loader from './elements/Loader.vue';
import { useContactStore, type IContact } from '@/store/useContactStore';
import { useSnackbarStore } from '@/store/useSnackbarStore';

const contactStore = useContactStore();
const { addSnackbar } = useSnackbarStore();
const contacts = ref<IContact[]>([]);
const loading = ref<boolean>(true);

onMounted(async () => {
    loading.value = true;
    try {
        await contactStore.fetchContacts();
        contacts.value = contactStore.contacts;
    } catch (error) {
        addSnackbar("Error while loading contacts", "error");
    }
    loading.value = false;
})

</script>

<template>
    <div class="contact-list">
        <Loader v-if="loading" />
        <h3 v-if="contacts.length == 0 && !loading">There are no contacts</h3>
        <ContactItem v-for="contact in contacts" :contact="contact" />
    </div>
</template>