/*
 * Copyright (c) 2013-2021 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.categorymanagement.repository.CategoryRepository;
import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.base.customermanagement.repositories.CustomerRepository;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.infrastructure.bootstrapers.BaseBootstrapper;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.ordermanagement.repositories.OrderRepository;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.shoppingcart.repositories.ShoppingCartRepository;
import eapli.base.surveys.repositories.SurveyAnswersRepository;
import eapli.base.surveys.repositories.SurveyRepository;
import eapli.base.surveys.repositories.SurveyTargetRepository;
import eapli.base.warehouse.repositories.AgvRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.InMemoryUserRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    static {
        // only needed because of the in memory persistence
        new BaseBootstrapper().execute();
    }

    @Override
    public UserRepository users(final TransactionalContext tx) {
        return new InMemoryUserRepository();
    }

    @Override
    public UserRepository users() {
        return users(null);
    }

    @Override
    public ClientUserRepository clientUsers(final TransactionalContext tx) {

        return new InMemoryClientUserRepository();
    }

    @Override
    public ClientUserRepository clientUsers() {
        return clientUsers(null);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return signupRequests(null);
    }



    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext tx) {
        return new InMemorySignupRequestRepository();
    }

    @Override
    public CategoryRepository categories(final TransactionalContext tx) {
        return new InMemoryCategoryRepository();
    }

    @Override
    public CategoryRepository categories() {
        return categories(null);
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        // in memory does not support transactions...
        return null;
    }

    @Override
    public ProductRepository products(TransactionalContext tx){return new InMemoryProductRepository();}

    @Override
    public ProductRepository products() {
        return products(null);
    }

    @Override
    public CustomerRepository customers(TransactionalContext autoTx) {
        return new InMemoryCustomerRepository();
    }

    @Override
    public CustomerRepository customers() {
        return customers(null);
    }

    @Override
    public OrderRepository orders(TransactionalContext autoTx) {
        return new InMemoryOrderRepository();
    }

    @Override
    public OrderRepository orders() { return orders(null); }

    @Override
    public AgvRepository agvs(TransactionalContext autoTx) {
        return new InMemoryAgvRepository();
    }

    @Override
    public AgvRepository agvs() {
        return agvs(null);
    }

    @Override
    public ShoppingCartRepository shoppingCart(TransactionalContext autoTx) {
        return new InMemoryShoppingCartRepository();
    }

    @Override
    public ShoppingCartRepository shoppingCart() {
        return shoppingCart(null);
    }

    @Override
    public SurveyRepository surveys(TransactionalContext autoTx) {
        return new InMemorySurveyRepository();
    }

    @Override
    public SurveyRepository surveys() {
        return surveys(null);
    }

    @Override
    public SurveyTargetRepository surveyTargets(TransactionalContext autoTx) {
        return new InMemorySurveyTargetsRepository();
    }

    @Override
    public SurveyTargetRepository surveyTargets() {
        return surveyTargets(null);
    }

    @Override
    public SurveyAnswersRepository surveyAnswers(TransactionalContext autoTx) {
        return new InMemorySurveyAnswersRepository();
    }

    @Override
    public SurveyAnswersRepository surveyAnswers() {
        return surveyAnswers(null);
    }

}
