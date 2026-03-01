/*
 * Adapter for NexT related_posts partial.
 *
 * NexT old template expects helper popular_posts_json(params, page),
 * while hexo-related-posts writes related paths into page.related_posts.
 */

'use strict';

hexo.extend.helper.register('popular_posts_json', function popularPostsJson(params = {}, page = {}) {
  const relatedPaths = Array.isArray(page.related_posts) ? page.related_posts : [];
  const maxCount = Number(params.maxCount) > 0 ? Number(params.maxCount) : 5;

  if (!relatedPaths.length) {
    return { json: [] };
  }

  const postsModel = this.site && this.site.posts;
  const posts = postsModel && typeof postsModel.toArray === 'function' ? postsModel.toArray() : [];
  const byPath = new Map(posts.map(post => [post.path, post]));

  const json = [];
  for (const relatedPath of relatedPaths) {
    if (json.length >= maxCount) break;

    const post = byPath.get(relatedPath);
    if (!post) continue;

    json.push({
      date: post.date ? this.date(post.date, 'YYYY-MM-DD') : '',
      img: '',
      title: post.title || '(no title)',
      path: this.url_for(post.path),
      excerpt: ''
    });
  }

  return { json };
});
