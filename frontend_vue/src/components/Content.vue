<template>
  <div>
    <table border="1" width="100%" style="border-collapse: collapse;">
      <tr>
        <th>Id</th>
        <th>Version</th>
        <th>Title</th>
        <th>Content</th>
        <th>Author</th>
        <th>Time</th>
        <th>actions</th>
      </tr>

      <tr v-for="article in articles">
        <td width="15%">{{ article.id }}</td>
        <td width="5%">{{ article.version }}</td>
        <td width="15%">{{ article.title }}</td>
        <td width="15%">{{ article.content }}</td>
        <td width="10%">{{ article.author }}</td>
        <td>{{ article.modifiedTime }}</td>
        <td width="120px">
          <input type="button" @click="selectArticle(article)" value="Select" />
          <input type="button" @click="deleteArticle(article.id)" value="Delete" />
        </td>
      </tr>
    </table>
    <br />
    <form>
      <table>
        <tr>
          <td width="20px">
            <label>Title</label><br/>
            <input type="text" name="title" v-model="formArticle.title" />
          </td>
          <td />
        </tr>
        <tr>
          <td>
            <label>Content</label><br/>
            <textarea rows="5" cols="30" name="content" v-model="formArticle.content" >
            </textarea>
          </td>
        </tr>
        <tr>
          <td>
            <label>Author</label><br/>
            <input type="text" name="author" v-model="formArticle.author" />
          </td>
        </tr>
        <tr>
          <td>
            <input
              v-if="!formArticle.id"
              type="button"
              @click="createArticle(formArticle)"
              value="Add"
            />
            <input
              v-if="formArticle.id"
              type="button"
              @click="updateArticle(formArticle)"
              value="Update"
            />
            <input type="button" @click="clearForm()" value="Clear" />
          </td>
          <td />
        </tr>
      </table>
    </form>
  </div>
</template>

<script>
import gql from "graphql-tag";
import { Article } from "@/models";
export default {
  data() {
    return {
      formArticle: new Article()
    };
  },
  apollo: {
    articles: gql`
      query {
        articles {
          id
          version
          title
          content
          author
          modifiedTime
        }
      }
    `
  },
  methods: {
    createArticle(articleInput) {
      // console.log(`Create contact: ${articleInput.title}`)
      this.$apollo.mutate({
        mutation: gql`
          mutation createArticle($article: ArticleInput!) {
            createArticle(article: $article) {
              id
              version
              title
              content
              author
              modifiedTime
            }
          }
        `,
        variables: {
          article: articleInput
        }
      });
      location.reload();
    },
    updateArticle(articleInput) {
      // console.log(`Update Article: # ${articleInput.id}`)
      this.$apollo.mutate({
        mutation: gql`
          mutation updateArticle($article: ArticleInput!) {
            updateArticle(article: $article) {
              id
              version
              title
              content
              author
              modifiedTime
            }
          }
        `,
        variables: {
          article: articleInput
        }
      });
      location.reload();
    },
    deleteArticle(id) {
      // console.log(`Delete Article: # ${id}`)
      this.$apollo.mutate({
        mutation: gql`
          mutation deleteArticle($id: ID!) {
            deleteArticle(id: $id)
          }
        `,
        variables: {
          id: id
        }
      });
      location.reload();
    },
    selectArticle(article) {
      this.formArticle.id = article.id;
      this.formArticle.version = article.version;
      this.formArticle.title = article.title;
      this.formArticle.content = article.content;
      this.formArticle.author = article.author;
      this.formArticle.modifiedTime = article.modifiedTime;
    },
    clearForm() {
      this.formArticle.id = null;
      this.formArticle.version = "";
      this.formArticle.title = "";
      this.formArticle.content = "";
      this.formArticle.author = "";
      this.formArticle.modifiedTime = "";
    }
  },
  created() {
    // console.log("created");
  }
};
</script>
<style>
</style>