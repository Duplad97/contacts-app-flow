import api from '@/config/api';
import { defineStore } from 'pinia';

export interface IContact {
    id: number
    name: string
    phone: string
    email: string
    image: string | null
}

export const useContactStore = defineStore('contactStore', {
    state: () => ({
        contacts: [] as IContact[],
    }),

    getters: {
        getContactById: (state) => (id: number): IContact | undefined => {
            return state.contacts.find((contact) => contact.id === id);
        },
    },

    actions: {
        async addContact(contact: IContact, imageFile: File | null) {
            const formData = new FormData();
            formData.append("name", contact.name);
            formData.append("email", contact.email);
            formData.append("phone", contact.phone);

            if (imageFile) {
                formData.append("image", imageFile);
            }
            const response = await api.post("/", formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            })
            this.contacts.push(response.data);
        },

        async updateContact(contact: IContact, imageFile: File | null) {
            const formData = new FormData();
            formData.append("name", contact.name);
            formData.append("email", contact.email);
            formData.append("phone", contact.phone);

            if (contact.image) {
                formData.append("image", contact.image);
            }

            if (!contact.image && !imageFile) {
                formData.append("image", "");
            }

            if (imageFile) {
                formData.append("image", imageFile);
            }

            const response = await api.put(`/${contact.id}`, formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            })

            const index = this.contacts.findIndex((c) => c.id === contact.id);
            if (index !== -1) {
                this.contacts[index] = response.data;
            }
        },

        async deleteContact(id: number) {
            await api.delete(`/${id}`);
            const index = this.contacts.findIndex((c) => c.id === id);
            this.contacts.splice(index, 1);
        },

        async fetchContacts() {
            try {
                const response = await api.get('/');
                this.contacts = response.data;
            } catch (error) {
                console.error(error);
            }
        }
    }
});
