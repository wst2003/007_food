<template>
    <nut-uploader
        v-model:file-list="images"
        accept="image/*"
        multiple
        :auto-upload="false"
        maximum="5"
        :url="uploadUrl"
        :data="formData" 
        ref="uploadRef"
        name='images'
        >
        </nut-uploader>
        <nut-button @click="submit"></nut-button>
    </template>
    
    
    <script setup>
    import {ref ,nextTick} from 'vue';
    const uploadUrl=ref('/api/sto/uploadImage')
    const images=ref([])
    const formData=ref({
     'com_id':'54',
    })
    const uploadRef = ref(null);
    const submit=()=>{
     formData.value.com_id = '66';
     nextTick(() => {
    console.log('DOM updated'); 
     });
     uploadRef.value.submit();
    }
    </script>