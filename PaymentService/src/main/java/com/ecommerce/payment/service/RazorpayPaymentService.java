package com.ecommerce.payment.service;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.payment.model.Transaction;
import com.ecommerce.payment.repository.TransactionRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class RazorpayPaymentService {

	@Value("${razorpay.keyId}")
	private String razorpayKeyId;

	@Value("${razorpay.keySecret}")
	private String razorpayKeySecret;

	@Autowired
	private TransactionRepo transactionRepo;

	public void createOrder(Map<String, Object> data) throws RazorpayException {
		Integer str = (Integer) data.get("orderId");
		int aid = str.intValue();
		double amt = Double.parseDouble(data.get("amount").toString());
		RazorpayClient client = new RazorpayClient("rzp_test_401cmKXEmF2LUE", "GDEjcUzDFhX5UWhjDMlGeffO");
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amt * 100);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "order_rcpttx87_");
		Order order = client.Orders.create(orderRequest);
		Transaction transaction = new Transaction();
		transaction.setRazorPayOrderId(order.get("id"));
		transaction.setOrderId(aid);
		transaction.setStatus("created");
		transactionRepo.save(transaction);
	}

	public void capturePayment(Map<String, Object> data) throws RazorpayException {
		String paymentId = data.get("payment_id").toString();
		double amount = Double.parseDouble(data.get("amount").toString());
		Integer orderId = (Integer) data.get("order_id");
		int aid = orderId.intValue();
		Transaction transaction = transactionRepo.findByOrderId(aid).get(0);
		transaction.setPaymentId(paymentId);
		transaction.setStatus(data.get("status").toString());
		transactionRepo.save(transaction);
		RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
		Map<String, Object> params = new HashMap<>();
		params.put("amount", amount * 100);
		razorpayClient.Payments.capture(paymentId, (JSONObject) params);
	}

}
