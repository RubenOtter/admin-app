<template>
    <template v-if="isEditing">
        <td><input v-model="username"/></td>
        <td><input v-model="pincode"/></td>
        <td style="text-align: right;">
            <i class="icon fas fa-check" @click="handleSubmit"></i>
            <i class="icon fas fa-times" @click="deleteEmployees"></i>
        </td>
    </template>
    <template v-else>
        <td>{{ this.username }}</td>
        <td>{{ this.pincode }}</td>
        <td style="text-align: right;"><i class="icon fas fa-edit" @click="changeToEdit"></i></td>
    </template>
</template>

<script>
import notification from '../../utils/NotificationUtil'
import LanguageUtil from '../../utils/LanguageUtil'

export default {
    name: 'Employee',
    emits: ['addEmployees', 'updateEmployees', 'getEmployees', 'deleteEmployees'],
    data() {
        return {
            text: LanguageUtil.getTextObject(),
            username: '',
            pincode: 0,
            isEditing: false
        }
    },
    props: {
        currentId: String,
        currentUsername: String,
        currentPincode: Number,
        isNew: Boolean
    },
    created() {
        this.username = this.currentUsername
        this.pincode = this.currentPincode 
        this.isEditing = this.isNew 
    },
    methods: {
        changeToEdit() {
            this.isEditing = true
        },
        handleSubmit() {
            if(!this.username.trim() || !this.pincode) {
                notification.showErrorNotification(this.text.EmpComp_FieldErr)
                return
            }

            if(this.isNew) {
                const employee = { 
                    username: this.username, 
                    pincode: parseInt(this.pincode)
                }

                this.$emit('addEmployees', employee)
            }
            else if(this.username !== this.currentUsername || this.pincode !== this.currentPincode) {
                const employee = { 
                    id: this.currentId,
                    username: this.username, 
                    pincode: parseInt(this.pincode)
                }
                
                this.$emit('updateEmployees', employee)
            }

            this.isEditing = false
        },
        deleteEmployees() {
            if(this.isNew) {
                this.$emit('getEmployees')
            }
            else {
                if(!confirm(this.text.EmpComp_DelConfirm + this.username)) {
                    return;
                }
                
                this.isEditing = false;           
                this.$emit('deleteEmployees', this.currentId);
            }
        }
    }
}
</script>

<style scoped>
    td {
        padding: 10px;
        font-size: 1.25rem;
        font-weight: normal;
        color: var(--text-color);
    }
    input {
        font-family: inherit;
        font-size: inherit;
        color: var(--primary-color);
        background-color: var(--secondary-color);
        border: 1px solid var(--text-color);
        width: 100%;
    }
    input:focus {
        outline: none;
    }
    .icon {
        color: var(--text-color);
        font-size: 1.2em;
        margin-left: 5px;
    }
    .icon:hover {
        color: var(--primary-color);
        cursor: pointer;
    }
</style>