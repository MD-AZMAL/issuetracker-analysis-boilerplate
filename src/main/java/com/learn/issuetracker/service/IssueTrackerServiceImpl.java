package com.learn.issuetracker.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.learn.issuetracker.exceptions.IssueNotFoundException;
import com.learn.issuetracker.model.Employee;
import com.learn.issuetracker.model.Issue;
import com.learn.issuetracker.repository.IssueRepository;

/*
 * This class contains functionalities for searching and analyzing Issues data Which is stored in a collection
 * Use JAVA8 STREAMS API to do the analysis
 * 
*/
public class IssueTrackerServiceImpl implements IssueTrackerService {

	/*
	 * CURRENT_DATE contains the date which is considered as todays date for this
	 * application Any logic which uses current date in this application, should
	 * consider this date as current date
	 */
	private static final String CURRENT_DATE = "2019-05-01";

	/*
	 * The issueDao should be used to get the List of Issues, populated from the
	 * file
	 */
	private IssueRepository issueDao;
	private LocalDate today;

	/*
	 * Initialize the member variables Variable today should be initialized with the
	 * value in CURRENT_DATE variable
	 */
	public IssueTrackerServiceImpl(IssueRepository issueDao) {
		this.today = LocalDate.parse(CURRENT_DATE);
		this.issueDao = issueDao;
	}

	/*
	 * In all the below methods, the list of issues should be obtained by used
	 * appropriate getter method of issueDao.
	 */
	/*
	 * The below method should return the count of issues which are closed.
	 */
	@Override
	public long getClosedIssueCount() {
		return Long.valueOf(this.getIssuesByStatus("CLOSED").size());
	}

	/*
	 * The below method should return the Issue details given a issueId. If the
	 * issue is not found, method should throw IssueNotFoundException
	 */

	@Override
	public Issue getIssueById(String issueId) throws IssueNotFoundException {

		Issue issue = this.issueDao.getIssues().stream().filter(I -> I.getIssueId().equals(issueId)).findFirst()
				.orElse(null);

		if (issue == null)
			throw new IssueNotFoundException();
		else
			return issue;
	}

	/*
	 * The below method should return the Employee Assigned to the issue given a
	 * issueId. It should return the employee in an Optional. If the issue is not
	 * assigned to any employee or the issue Id is incorrect the method should
	 * return empty optional
	 */
	@Override
	public Optional<Employee> getIssueAssignedTo(String issueId) {
		Optional<Employee> employee = Optional.empty();
		try {
			Issue I = this.getIssueById(issueId);

			if (I.getAssignedTo() != null)
				employee = Optional.of(I.getAssignedTo());

		} catch (IssueNotFoundException e) {
			e.printStackTrace();
		}

		return employee;
	}

	/*
	 * The below method should return the list of Issues given the status. The
	 * status can contain values OPEN / CLOSED
	 */
	@Override
	public List<Issue> getIssuesByStatus(String status) {

		return this.issueDao.getIssues().stream().filter(I -> I.getStatus().equals(status))
				.collect(Collectors.toList());

	}

	/*
	 * The below method should return a LinkedHashSet containing issueid's of open
	 * issues in the ascending order of expected resolution date
	 */
	@Override
	public Set<String> getOpenIssuesInExpectedResolutionOrder() {
		List<Issue> openIssues = this.getIssuesByStatus("OPEN");

		Comparator<Issue> resolutionDate = (Issue I1, Issue I2) -> I1.getExpectedResolutionOn()
				.compareTo(I2.getExpectedResolutionOn());

		Collections.sort(openIssues,resolutionDate);

		return openIssues.stream().map(Issue::getIssueId)
				.collect(Collectors.toCollection(LinkedHashSet::new));

	}

	/*
	 * The below method should return a List of open Issues in the descending order
	 * of Priority and ascending order of expected resolution date within a priority
	 */
	@Override
	public List<Issue> getOpenIssuesOrderedByPriorityAndResolutionDate() {
		List<Issue> openIssues = this.getIssuesByStatus("OPEN");

		Comparator<Issue> resolutionDate = (Issue I1, Issue I2) -> I1.getExpectedResolutionOn()
				.compareTo(I2.getExpectedResolutionOn());

		Comparator<Issue> priority = (Issue I1, Issue I2) -> I1.getPriority().compareTo(I2.getPriority());

		Collections.sort(openIssues, priority.reversed().thenComparing(resolutionDate));

		return openIssues;
	}

	/*
	 * The below method should return a List of 'unique' employee names who have
	 * issues not closed even after 7 days of Expected Resolution date. Consider the
	 * current date as 2019-05-01
	 */
	@Override
	public List<String> getOpenIssuesDelayedbyEmployees() {

		List<Issue> openIssues = this.getIssuesByStatus("OPEN");

		return openIssues.stream().filter(issue -> {
			LocalDate resolutionDate = issue.getExpectedResolutionOn();
			return (this.today.compareTo(resolutionDate) > 0
					&& ChronoUnit.DAYS.between(resolutionDate, this.today) > 7);
		}).map(issue -> issue.getAssignedTo().getName()).distinct().collect(Collectors.toList());
	}

	/*
	 * The below method should return a map with key as issueId and value as
	 * assigned employee Id. THe Map should contain details of open issues having
	 * HIGH priority
	 */
	@Override
	public Map<String, Integer> getHighPriorityOpenIssueAssignedTo() {
		List<Issue> openIssues = this.getIssuesByStatus("OPEN");
		Map<String, Integer> highPriorityMap = new HashMap<String, Integer>();

		for (Issue I : openIssues) {
			if (I.getPriority().equals("HIGH")) {
				highPriorityMap.put(I.getIssueId(), I.getAssignedTo().getEmplId());
			}
		}

		return highPriorityMap;
	}

	/*
	 * The below method should return open issues grouped by priority in a map. The
	 * map should have key as issue priority and value as list of open Issues
	 */
	@Override
	public Map<String, List<Issue>> getOpenIssuesGroupedbyPriority() {
		List<Issue> openIssues = this.getIssuesByStatus("OPEN");
		Map<String, List<Issue>> priorityMap = new HashMap<String, List<Issue>>();

		priorityMap = openIssues.stream().collect(Collectors.groupingBy(I -> String.valueOf(I.getPriority())));

		return priorityMap;
	}

	/*
	 * The below method should return count of open issues grouped by priority in a
	 * map. The map should have key as issue priority and value as count of open
	 * issues
	 */
	@Override
	public Map<String, Long> getOpenIssuesCountGroupedbyPriority() {
		Map<String, List<Issue>> priorityMap = this.getOpenIssuesGroupedbyPriority();
		Map<String, Long> priorityMapCount = new HashMap<String, Long>();

		for (Map.Entry<String, List<Issue>> m : priorityMap.entrySet()) {
			priorityMapCount.put(m.getKey(), Long.valueOf(m.getValue().size()));
		}

		return priorityMapCount;
	}

	/*
	 * The below method should provide List of issue id's(open), grouped by location
	 * of the assigned employee. It should return a map with key as location and
	 * value as List of issue Id's of open issues
	 */
	@Override
	public Map<String, List<String>> getOpenIssueIdGroupedbyLocation() {
		Set<String> locations = new HashSet<String>();
		List<Issue> openIssues = this.getIssuesByStatus("OPEN");
		Map<String, List<String>> openIssuesByLocation = new HashMap<String, List<String>>();

		for (Issue I : openIssues) {
			locations.add(I.getAssignedTo().getLocation());
		}

		for (String location : locations) {
			List<String> issueIds = openIssues.stream().filter(I -> I.getAssignedTo().getLocation().equals(location))
					.map(I -> I.getIssueId()).collect(Collectors.toList());
			openIssuesByLocation.put(location, issueIds);
		}

		return openIssuesByLocation;
	}

	/*
	 * The below method should provide the number of days, since the issue has been
	 * created, for all high/medium priority open issues. It should return a map
	 * with issueId as key and number of days as value. Consider the current date as
	 * 2019-05-01
	 */
	@Override
	public Map<String, Long> getHighMediumOpenIssueDuration() {
		Map<String, Long> highMedIssueDurationMap = new HashMap<String, Long>();

		List<Issue> openIssues = this.getIssuesByStatus("OPEN");

		List<Issue> highMediumIssues = openIssues.stream().filter(I -> !I.getPriority().equals("LOW"))
				.collect(Collectors.toList());

		for (Issue I : highMediumIssues) {
			Long duration = Long.valueOf(ChronoUnit.DAYS.between(I.getCreatedOn(), this.today));
			highMedIssueDurationMap.put(I.getIssueId(), duration);
		}

		return highMedIssueDurationMap;
	}
}