/**
 * Transactional.java
 */
package com.ceco.ebookie.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;

/**
 * <p>
 * A central abstract class for managing database transaction via hibernate
 * sessions in a short and convenient way. This class is intended to be
 * instanced (implemented) as anonymous class in method body. A typical usage
 * could be as follows:
 *
 * <pre>
 * {@code
 * public void myMethod() {
 *     new Transactional<Void>(false) {
 *         @Override
 *         protected void doInTransaction(Session session) {
 *             // your code here
 *         }
 *     }.execute();
 * }
 *
 * }
 * </pre>
 *
 * </p>
 *
 * @param <T>
 *            the type of object that this transaction will return on execution
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 02-Jan-2016
 */
public abstract class Transactional<T> {

    private T returnValue;

    /**
     * Construct a default transactional that is not using read only connections
     * to the database.
     */
    public Transactional() {
        super();
    }

    /**
     * Should implement transactional logic.
     *
     * @param session
     *            the session associated with this transaction
     * @throws Exception
     *             if there was an error in the transaction
     */
    protected abstract void doInTransaction(final Session session)
            throws Exception;

    /**
     *
     * Sets the return value from this transaction. Most of the time this should
     * be called in {@link #doInTransaction(Session)} and it will be propagated
     * as a return type to {@link #execute()}
     *
     * @param returnValue
     *            the return value from this transactional execution.
     */
    protected final void setReturnValue(T returnValue) {
        this.returnValue = returnValue;
    }

    /**
     * Make actual execution in scope of single transaction.
     *
     * @return the result from this transaction or null if no result was set
     *
     */
    public final T execute() {
        Session s = HibernateUtil.getSession();
        ManagedSessionContext.bind(s);
        Transaction t = s.getTransaction();
        try {
            t.begin();
            doInTransaction(s);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw new RuntimeException("Transaction failed and was rollbacked", e);
        } finally {
            ManagedSessionContext.unbind(HibernateUtil.getSessionFactory());
            s.close();
        }
        return returnValue;
    }
}
