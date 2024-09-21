<script setup lang="ts">
import type { IContact } from '@/interfaces';
import ContactItem from './ContactItem.vue';
import { onMounted, ref } from 'vue';
import api from '@/config/api';

const contacts = ref<IContact[]>([]);

onMounted(async() => {
    try {
      const response = await api.get('/');
      contacts.value = response.data;
    } catch (error) {
      console.error(error);
    }
})

</script>

<template>
    <div class="contact-list">
        <h3 v-if="contacts.length == 0">There are no contacts</h3>
        <ContactItem v-for="contact in contacts" :contact="contact" />
    </div>
</template>