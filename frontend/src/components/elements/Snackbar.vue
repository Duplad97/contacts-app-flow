<script lang="ts">
import { defineComponent } from 'vue';
import { useSnackbarStore } from '@/store/useSnackbarStore';

export default defineComponent({
    setup() {
        const snackbarStore = useSnackbarStore();

        return {
            snackbars: snackbarStore.snackbars,
            removeSnackbar: snackbarStore.removeSnackbar,
        };
    },
});
</script>

<template>
    <transition-group name="slide" tag="div" class="snackbar-container">
        <div v-for="snackbar in snackbars" :key="snackbar.id" class="snackbar" :class="snackbar.type"
            @click="removeSnackbar(snackbar.id)">
            {{ snackbar.message }}
        </div>
    </transition-group>
</template>