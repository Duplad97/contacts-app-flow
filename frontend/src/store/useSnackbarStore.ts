import { defineStore } from 'pinia';
import { ref } from 'vue';

interface Snackbar {
    id: number;
    message: string;
    type: 'success' | 'error' | 'info' | 'warning';
}

export const useSnackbarStore = defineStore('snackbar', () => {
    const snackbars = ref<Snackbar[]>([]);

    const addSnackbar = (message: string, type: Snackbar['type'] = 'info') => {
        const id = Date.now();
        snackbars.value.push({ id, message, type });

        setTimeout(() => {
            removeSnackbar(id);
        }, 4000);
    };

    const removeSnackbar = (id: number) => {
        const index = snackbars.value.findIndex(snackbar => snackbar.id === id);
        snackbars.value.splice(index, 1);
    };

    return { snackbars, addSnackbar, removeSnackbar };
});
