<template>
    <v-card outlined>
        <v-card-title>
            CreateDelivery
        </v-card-title>

        <v-card-text>
            <String label="DeliveryId" v-model="value.deliveryId" :editMode="editMode"/>
            <Number label="Qty" v-model="value.qty" :editMode="editMode"/>
            <String label="ProductId" v-model="value.productId" :editMode="editMode"/>
            <String label="ProductName" v-model="value.productName" :editMode="editMode"/>
            <String label="OrderId" v-model="value.orderId" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="createDelivery"
            >
                CreateDelivery
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
        name: 'CreateDeliveryCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.deliveryId = '';
            this.value.qty = 0;
            this.value.productId = '';
            this.value.productName = '';
            this.value.orderId = '';
        },
        watch: {
        },
        methods: {
            createDelivery() {
                this.$emit('createDelivery', this.value);
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

