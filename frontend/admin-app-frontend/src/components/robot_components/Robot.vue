<template>
    <template v-if="isEditing && isNew">
        <td><input v-model="name"/></td>
        <td><input v-model="id"/></td>
        <td style="text-align: right;">
            <i class="icon fas fa-check" @click="handleSubmit"/>
            <i class="icon fas fa-times" @click="deleteRobot"/>
        </td>
    </template>
    <template v-else-if="isEditing && !isNew">
        <td><input v-model="name"/></td>
        <td>{{ currentId }}</td>
        <td style="text-align: right;">
            <i class="icon fas fa-check" @click="handleSubmit"/>
            <i class="icon fas fa-times" @click="deleteRobot"/>
        </td>
    </template>
    <template v-else>
        <td>{{ this.name }}</td>
        <td>{{ this.id }}</td>
        <td style="text-align: right;"><i class="icon fas fa-edit" @click="changeToEdit" /></td>
    </template>
</template>

<script>
import notification from '../../utils/NotificationUtil'
import LanguageUtil from '../../utils/LanguageUtil'

export default {
    name: 'Robot',
    emits: ['addRobot', 'updateRobot', 'getRobots', 'deleteRobot'],
    data() {
        return {
            text: LanguageUtil.getTextObject(),
            name: '',
            id: '',
            isEditing: false
        }
    },
    props: {
        currentName: String,
        currentId: String,
        isNew: Boolean
    },
    created(){
        this.name = this.currentName
        this.id = this.currentId
        this.isEditing = this.isNew
    },
    methods: {
        changeToEdit(){
            this.isEditing = true
        },
        handleSubmit(){
            if(!this.id.trim() || !this.name.trim()){
                notification.showErrorNotification(this.text.RobotComp_FieldErr)
                return
            }

            const robot = { 
                id: this.id, 
                name: this.name 
            }

            if(this.isNew){
                this.$emit('addRobot', robot)
            }
            else if(this.name !== this.currentName){
                this.$emit('updateRobot', robot)
            }

            this.isEditing = false
        },
        deleteRobot(){
            if(this.isNew){
                this.$emit('getRobots')
            }
            else{
                if(!confirm(this.text.RobotComp_Delete + this.name + ' #' + this.id + '?')){
                    return;
                }

                this.isEditing = false;
                this.$emit('deleteRobot', this.id)
            }
        }
    }
}
</script>

<style scoped>
    td{
        padding: 10px;
        font-size: 1.25rem;
        font-weight: normal;
        color: var(--text-color);
    }

    input{
        font-family: inherit;
        font-size: inherit;
        color: var(--primary-color);
        background-color: var(--secondary-color);
        border: 1px solid var(--text-color);
        width: 100%;
    }

    input:focus{
        outline: none;
    }

    .icon{
        color: var(--text-color);
        font-size: 1.2em;
        margin-left: 5px;
    }

        .icon:hover{
            color: var(--primary-color);
            cursor: pointer;
        }
</style>