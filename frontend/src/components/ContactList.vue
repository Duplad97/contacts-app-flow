<script setup lang="ts">
import type { IContact } from '@/interfaces';
import ContactItem from './ContactItem.vue';
import { onMounted, ref } from 'vue';
import api from '@/config/api';
import Loader from './elements/Loader.vue';

const contacts = ref<IContact[]>([]);
const loading = ref<boolean>(true);

onMounted(async() => {
  loading.value = true;
    try {
      const response = await api.get('/');
      contacts.value = response.data;
    } catch (error) {
      console.error(error);
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