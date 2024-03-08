package org.tju.food_007.service.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.repository.sto.StoCategoriesInformationRepository;
import org.tju.food_007.repository.sto.StoinformationdetailRepository;

@Service
public class StoinformationdetailService {
    @Autowired
    StoinformationdetailRepository stoinformationdetailRepository;

    @Autowired
    StoCategoriesInformationRepository stoCategoriesInformationRepository;









}
