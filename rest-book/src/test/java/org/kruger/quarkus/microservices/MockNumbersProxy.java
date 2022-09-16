package org.kruger.quarkus.microservices;

import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.kruger.quarkus.microservices.book.IsbnThirteen;
import org.kruger.quarkus.microservices.book.NumbersProxy;

@Mock
@RestClient
public class MockNumbersProxy implements NumbersProxy {
    @Override
    public IsbnThirteen generateIsbnNumbers() {
        IsbnThirteen isbnThirteen = new IsbnThirteen();
        isbnThirteen.isbn13 = "13-mock";
        return isbnThirteen;
    }
}
