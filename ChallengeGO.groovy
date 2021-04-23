def archivo = new File("archivo.csv")
def access_token = 'APP_USR-12345678-031820-X-12345678'
def orderIdArray = ["4234456969","4234458807","4234454929","4234456943","4234455939","4234467429"]


archivo.write("ORDER ID, ID, TITULO, VARIACION, MONTO TOTAL, MONEDA, TIPO LOGISTICA, DESTINO ENVIO, METODO DE ENVIO, IDAGENCIA, CARRIER AGENCIA, DIRECCION \n")
orderIdArray.each{ orderId ->
	urlOrder = "curl -X GET -H Authorization:Bearer ${access_token}" + "https://api.mercadolibre.com/orders/${orderId}"
	if(urlOrder)
	{
		orderUrl = urlOrder.order_items.item
		orderPayment = urlOrder.payments
		shipment_id  = urlOrder.shipping.id

		urlShipping = "curl -X GET -H Authorization: Bearer ${access_token}" + "https://api.mercadolibre.com/shipments/${shipment_id}"
		destino = urlShipping.destination.shipping_address

		archivo.append("${orderId}, ${orderUrl.id}, ${orderUrl.title}, ${orderUrl.variation_attributes}, ${orderPayment.transaction_amount}, ${orderPayment.currency_id}, ${urlShipping.logistic.type}, ${urlShipping.logistic.direction}, ${urlShipping.lead_time.shipping_method.name}, ${destino.agency.agency_id}, ${destino.agency.carrier_id}, ${destino.address_line}  \n")
	}
	else
		archivo.write("${orderId} order no encontrada\n")
}