package com.lms.services;

import com.lms.domain.Publisher;

import java.util.List;

public interface PublisherService {
    Publisher addPublisher(Publisher publisher);
    Publisher updatePublisher(Long id, Publisher publisher);
    Publisher getPublisherById(Long id);
    List<Publisher> getAllPublishers();
    void deletePublisher(Long id);
}
