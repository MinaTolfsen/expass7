package no.hvl.dat250.jpa.tutorial.creditcards.driver;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import no.hvl.dat250.jpa.tutorial.creditcards.*;

import java.util.List;

public class CreditCardsMain {

  static final String PERSISTENCE_UNIT_NAME = "jpa-tutorial";

  public static void main(String[] args) {
    try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(
        PERSISTENCE_UNIT_NAME); EntityManager em = factory.createEntityManager()) {
      em.getTransaction().begin();
      createObjects(em);
      em.getTransaction().commit();
    }

  }

  private static void createObjects(EntityManager em) {
    // create customer
    Customer customer = new Customer();
    customer.setName("Max Mustermann");

    // create adress
    Address address = new Address();
    address.setStreet("Inndalsveien");
    address.setNumber(28);
    address.addOwner(customer);
    customer.setAddresses(address);

    // create pincode
    Pincode pincode = new Pincode();
    pincode.setCode("123");
    pincode.setCount(1);

    // create first creditcard
    CreditCard creditCard1 = new CreditCard();
    creditCard1.setNumber(12345);
    creditCard1.setBalance(-5000);
    creditCard1.setCreditLimit(-10000);
    creditCard1.setPincode(pincode);
    creditCard1.setCustomer(customer);

    // create second creditcard
    CreditCard creditCard2 = new CreditCard();
    creditCard2.setNumber(123);
    creditCard2.setBalance(1);
    creditCard2.setCreditLimit(2000);
    creditCard2.setPincode(pincode);
    creditCard2.setCustomer(customer);

    // create bank
    Bank bank = new Bank();
    bank.setName("Pengebank");
    creditCard1.setOwningBank(bank);
    creditCard2.setOwningBank(bank);

    // add the credit cards to the bank's card collection
    bank.setCreditCards(creditCard1);
    bank.setCreditCards(creditCard2);
    customer.setCreditCards(creditCard1);
    customer.setCreditCards(creditCard2);

    // persist the objects
    em.persist(customer);
    em.persist(address);
    em.persist(pincode);
    em.persist(creditCard1);
    em.persist(creditCard2);
    em.persist(bank);

  }
}
