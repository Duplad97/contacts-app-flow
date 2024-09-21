<script setup lang="ts">
import ContactList from '@/components/ContactList.vue';
import ContactsHeader from '@/components/ContactsHeader.vue';
import Button from '@/components/elements/Button.vue';
import Divider from '@/components/elements/Divider.vue';
import IconArrowBack from '@/components/icons/IconArrowBack.vue';
import IconLightMode from '@/components/icons/IconLightMode.vue';
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
            <ContactList />
        </div>
    </div>
</template>