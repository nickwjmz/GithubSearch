package com.example.githubsearch.model

data class UserRepoResponse(
    var incomplete_results: Boolean,
    var items: List<Repo>,
    var total_count: Int
)

data class Owner(
    var avatar_url: String,
    var events_url: String,
    var followers_url: String,
    var following_url: String,
    var gists_url: String,
    var gravatar_id: String,
    var html_url: String,
    var id: Int,
    var login: String,
    var node_id: String,
    var organizations_url: String,
    var received_events_url: String,
    var repos_url: String,
    var site_admin: Boolean,
    var starred_url: String,
    var subscriptions_url: String,
    var type: String,
    var url: String
)

data class Repo(
    var archive_url: String,
    var archived: Boolean,
    var assignees_url: String,
    var blobs_url: String,
    var branches_url: String,
    var clone_url: String,
    var collaborators_url: String,
    var comments_url: String,
    var commits_url: String,
    var compare_url: String,
    var contents_url: String,
    var contributors_url: String,
    var created_at: String,
    var default_branch: String,
    var deployments_url: String,
    var description: Any,
    var disabled: Boolean,
    var downloads_url: String,
    var events_url: String,
    var fork: Boolean,
    var forks: Int,
    var forks_count: Int,
    var forks_url: String,
    var full_name: String,
    var git_commits_url: String,
    var git_refs_url: String,
    var git_tags_url: String,
    var git_url: String,
    var has_downloads: Boolean,
    var has_issues: Boolean,
    var has_pages: Boolean,
    var has_projects: Boolean,
    var has_wiki: Boolean,
    var homepage: Any,
    var hooks_url: String,
    var html_url: String,
    var id: Int,
    var issue_comment_url: String,
    var issue_events_url: String,
    var issues_url: String,
    var keys_url: String,
    var labels_url: String,
    var language: String,
    var languages_url: String,
    var license: Any,
    var merges_url: String,
    var milestones_url: String,
    var mirror_url: Any,
    var name: String,
    var node_id: String,
    var notifications_url: String,
    var open_issues: Int,
    var open_issues_count: Int,
    var owner: Owner,
    var `private`: Boolean,
    var pulls_url: String,
    var pushed_at: String,
    var releases_url: String,
    var score: Double,
    var size: Int,
    var ssh_url: String,
    var stargazers_count: Int,
    var stargazers_url: String,
    var statuses_url: String,
    var subscribers_url: String,
    var subscription_url: String,
    var svn_url: String,
    var tags_url: String,
    var teams_url: String,
    var trees_url: String,
    var updated_at: String,
    var url: String,
    var watchers: Int,
    var watchers_count: Int
)