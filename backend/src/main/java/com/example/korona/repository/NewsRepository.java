package com.example.korona.repository;

import com.example.korona.dto.NewsDTO;
import com.example.korona.model.News;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends MongoRepository<News, String> {
        @Aggregation(pipeline = {
                        "{ $group: { " +
                                        "_id: '$date', " +
                                        "date: { $first: '$date' }, " +
                                        "infected: { $sum: '$infected' }, " +
                                        "death: { $sum: '$death' }, " +
                                        "discharged: { $sum: '$discharged' } " +
                                        "} }",
                        "{ $sort: { '_id': 1 } }"
        })
        List<NewsDTO> findNewsGroupedByDate();

        @Aggregation(pipeline = {
                        "{ $match: { city: ?0 } }",
                        "{ $group: { " +
                                        "_id: '$date', " +
                                        "date: { $first: '$date' }, " +
                                        "infected: { $sum: '$infected' }, " +
                                        "death: { $sum: '$death' }, " +
                                        "discharged: { $sum: '$discharged' } " +
                                        "} }",
                        "{ $sort: { '_id': 1 } }"
        })
        List<NewsDTO> findNewsGroupedByCity(String city);
}