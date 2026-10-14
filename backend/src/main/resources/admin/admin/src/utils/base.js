const base = {
    get() {
        return {
            url : "http://localhost:8080/springboot05qmpxx3/",
            name: "springboot05qmpxx3",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/springboot05qmpxx3/front/h5/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "鸟鸣识别"
        } 
    }
}
export default base
