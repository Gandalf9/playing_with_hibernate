package com.yatin.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Transaction;

import com.yatin.example.domain.Message;
import com.yatin.example.util.HibernateUtil;

public class HelloWorld {

	public static void main(String[] args) {
//		new HelloWorld().createAndSave();
//		new HelloWorld().createMany();
		new HelloWorld().jbossCreation();
		
	}
	
	public void jbossCreation() {
		EJB3Sta
	}
	
	public void createMany()
	{
		EntityManager session1 = Persistence.createEntityManagerFactory("helloworld").createEntityManager();
		
		EntityTransaction tx = session1.getTransaction();
		tx.begin();
		
		session1.persist(new Message("Hello World1", 1));
		session1.persist(new Message("Hello World2", 2));
		session1.persist(new Message("Hello World3", 3));
		
		Message toBeChangedMessage = (Message) session1.find(Message.class, 1L);
		
		toBeChangedMessage.setText("Something else");
		
		session1.flush();
		
		tx.commit();
		
		session1.close();

		Persistence.createEntityManagerFactory("helloworld").close();
	}
	
	
	public void createAndSave() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloworld");
		EntityManager session = emf.createEntityManager();

		EntityTransaction tx = session.getTransaction();
		tx.begin();

		Message msg = new Message("Hello World", 1);

		session.persist(msg);

		tx.commit();
		session.close();

		EntityManager newSession = emf.createEntityManager();

		EntityTransaction newTranscation = newSession.getTransaction();
		newTranscation.begin();

		List<Message> messages = newSession.createQuery(
				"select m from Message m order by m.text asc").getResultList();

		System.out.println(messages.size() + " message(s) found:");

		for (Message message : messages) {
			System.out.println(message);
		}

		newTranscation.commit();
		newSession.close();

		EntityManager thirdSession = emf.createEntityManager();
		EntityTransaction thirdTx = thirdSession.getTransaction();
		thirdTx.begin();

		msg = (Message) thirdSession.find(Message.class, msg.getId());
		msg.setText("Greetings Earthling");

		msg.setNextMessage(new Message("Take me to your leader", 4));

		thirdTx.commit();
		thirdSession.close();
	}
}
