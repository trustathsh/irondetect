/*
 * #%L
 * =====================================================
 *   _____                _     ____  _   _       _   _
 *  |_   _|_ __ _   _ ___| |_  / __ \| | | | ___ | | | |
 *    | | | '__| | | / __| __|/ / _` | |_| |/ __|| |_| |
 *    | | | |  | |_| \__ \ |_| | (_| |  _  |\__ \|  _  |
 *    |_| |_|   \__,_|___/\__|\ \__,_|_| |_||___/|_| |_|
 *                             \____/
 * 
 * =====================================================
 * 
 * Hochschule Hannover
 * (University of Applied Sciences and Arts, Hannover)
 * Faculty IV, Dept. of Computer Science
 * Ricklinger Stadtweg 118, 30459 Hannover, Germany
 * 
 * Email: trust@f4-i.fh-hannover.de
 * Website: http://trust.f4.hs-hannover.de/
 * 
 * This file is part of irondetect, version 0.0.10, 
 * implemented by the Trust@HsH research group at the Hochschule Hannover.
 * %%
 * Copyright (C) 2010 - 2016 Trust@HsH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package de.hshannover.f4.trust.irondetect.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import de.hshannover.f4.trust.ifmapj.messages.ResultItem;
import de.hshannover.f4.trust.ifmapj.messages.SearchResult;

/**
 * 
 * @author Marcel Reichenbach
 *
 */
public class SearchResultMock implements IfmapMock<SearchResult> {

	private String mSerachResultName;

	private SearchResult mSearchResult_mock;

	private List<ResultItem> mResultItems;

	public SearchResultMock(String searchResultName, SearchResult.Type type) {
		mSearchResult_mock = mock(SearchResult.class);
		mSerachResultName = searchResultName;
		mResultItems = new ArrayList<ResultItem>();

		when(mSearchResult_mock.getResultItems()).thenReturn(mResultItems);
		when(mSearchResult_mock.getType()).thenReturn(type);
		when(mSearchResult_mock.getName()).thenReturn(mSerachResultName);
	}

	public SearchResultMock(String searchResultName, SearchResult.Type type, List<ResultItem> resultItems) {
		this(searchResultName, type);

		mResultItems = resultItems;
		when(mSearchResult_mock.getResultItems()).thenReturn(mResultItems);
	}

	public void addResultItem(ResultItem resultItem) {
		mResultItems.add(resultItem);
		when(mSearchResult_mock.getResultItems()).thenReturn(mResultItems);
	}

	@Override
	public SearchResult getMock() {
		return mSearchResult_mock;
	}

}
