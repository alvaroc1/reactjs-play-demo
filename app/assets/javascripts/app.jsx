"use strict";

class MessageList extends React.Component {
  render () {
    return (
	  <div id='messages'>
	    {this.props.messages.map(m => <div className='message'>{m}</div>)}
	  </div>
    );
  }
}

class PostForm extends React.Component {
  render () {
    return (
      <form onSubmit={this.props.handleSubmit}>
        <input name='message' />
      </form>
    );
  }
}

class ChatPanel extends React.Component {
  constructor () {
    super();
    this.state = {messages: []};
  }
  
  componentDidMount () {
	this.socket = new WebSocket("ws://localhost:9000/socket");
	this.socket.onopen = (ev) => {
	  ev.currentTarget.send('join');
	};
	
	this.socket.onmessage = ((ev) => {
	  console.log(ev.data);
	  this.setState({messages: JSON.parse(ev.data)});
	}).bind(this);
  }
  
  handleSubmit (ev) {
    ev.preventDefault();
    this.socket.send(ev.target['message'].value);
  }
  
  render () {
    return (
  	  <div>
    	    <MessageList messages={this.state.messages}/>
    	    <PostForm handleSubmit={this.handleSubmit.bind(this)}/>
    	  </div>
    );
  }
}

React.render(
  <ChatPanel/>,
  document.body
);
