package com.bigbazaar.backend.services;

import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.bigbazaar.backend.domain.SearchOrganizationResult;

public interface SearchService {
	ResponseEntity<Set<SearchOrganizationResult>> searchByQuery(String query);
	ResponseEntity<Set<SearchOrganizationResult>> searchByCategory(String categoryName);
	ResponseEntity<Set<SearchOrganizationResult>> searchByQueryAndLocation(String query, Integer locationLimit);
	ResponseEntity<Set<SearchOrganizationResult>> searchOrgByLiteral(String query);
}
