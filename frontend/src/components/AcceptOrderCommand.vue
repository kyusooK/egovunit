<template>
    <v-card outlined>
        <v-card-title>
            AcceptOrder
        </v-card-title>

        <v-card-text>
            <String label="ProductName" v-model="value.productName" :editMode="editMode"/>
            <String label="Qty" v-model="value.qty" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="acceptOrder"
            >
                AcceptOrder
            </v-btn>
            
            <v-btn
                    color="primary"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'AcceptOrderCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.productName = '';
            this.value.qty = '';
        },
        watch: {
        },
        methods: {
            acceptOrder() {
                this.$emit('acceptOrder', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

