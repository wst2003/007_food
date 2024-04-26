<template>
    <nut-uploader
        v-model:file-list="images"
        accept="image/*"
        multiple
        :auto-upload="false"
        maximum="5"
        name='images'
        >
        </nut-uploader>
        <nut-button @click="submit"></nut-button>
    </template>
    
    
    <script setup>
    import {ref } from 'vue';
    import axios from 'axios';
    import globalData from "../../global.js"
    const BaseUrl = globalData.BaseUrl

    // const BaseUrl = "http://100.80.74.33:8000"
    const images=ref([])
    const submit=()=>{
        const formData = new FormData();
        var count=0;
        images.value.forEach((file) => {
        console.log(file.url);
        const pic=dataURLtoFile(file.url,'pic'+count.toString()+'.jpg')
        formData.append('images', pic); // 将文件添加到FormData中
        count++;
    });
        formData.append('com_id',102)
        axios.post(BaseUrl+'/api/sto/uploadImage',  formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
        }
        })
          .then(response => {
            console.log(response.msg)
          })
    }

    function dataURLtoFile(dataurl, filename) {
    // 获取到base64编码
    const arr = dataurl.split(',')
    // 将base64编码转为字符串
    const bstr = window.atob(arr[1])
    let n = bstr.length
    const u8arr = new Uint8Array(n) // 创建初始化为0的，包含length个元素的无符号整型数组
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
    }
    return new File([u8arr], filename, {
        type: 'image/jpeg',
    })
    }

    </script>