<script setup lang="ts">
import ContactItem from '@/components/ContactItem.vue';
import ContactsHeader from '@/components/ContactsHeader.vue';
import Button from '@/components/elements/Button.vue';
import Divider from '@/components/elements/Divider.vue';
import IconArrowBack from '@/components/icons/IconArrowBack.vue';
import IconLightMode from '@/components/icons/IconLightMode.vue';
import type { IContact } from '@/interfaces';

const tempContact:IContact = {
    name: "John Doe",
    phone: "+36 10 123 4567",
    email: "john@doe.com",
    image: "/src/assets/images/contact-image.png",
} 
</script>

<template>
    <div class="main-page">
        <Divider position="22.5%" />
        <Divider position="77.5%" />
        <header>
            <div class="left-panel">
                <Button :icon=IconArrowBack />
            </div>
            <ContactsHeader />
            <div class="right-panel">
                <Button :icon=IconLightMode />
            </div>
        </header>

        <div class="content">
            <div class="contact-list">
                <ContactItem :contact="{ ...tempContact }" />
                <ContactItem :contact="{ ...tempContact }" />
                <ContactItem :contact="{ ...tempContact }" />
            </div>
        </div>
    </div>
</template>