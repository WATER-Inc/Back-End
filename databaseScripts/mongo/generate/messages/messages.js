db.messages.insertMany([
  {
    sender_id: 1,
    chat_id: 1,
    content: 'Hello, everyone!',
    created_date: ISODate('2023-05-01T10:30:00Z')
  },
  {
    sender_id: 2,
    chat_id: 1,
    content: 'Hey there!',
    created_date: ISODate('2023-05-02T11:45:00Z')
  },
  {
    sender_id: 3,
    chat_id: 1,
    content: 'How are you all doing today?',
    created_date: ISODate('2023-05-03T14:20:00Z')
  },
  {
    sender_id: 4,
    chat_id: 1,
    content: 'I have a question about the project we are working on...',
    created_date: ISODate('2023-05-04T16:10:00Z')
  },
  {
    sender_id: 5,
    chat_id: 1,
    content: 'I think we should have a meeting to discuss...',
    created_date: ISODate('2023-05-05T09:00:00Z')
  },
  {
    sender_id: 3,
    chat_id: 2,
    content: 'Welcome to the new chat room!',
    created_date: ISODate('2023-05-06T10:30:00Z')
  },
  {
    sender_id: 6,
    chat_id: 2,
    content: 'Thanks for inviting me!',
    created_date: ISODate('2023-05-07T11:00:00Z')
  },
  {
    sender_id: 7,
    chat_id: 2,
    content: 'I am looking forward to collaborating with you all.',
    created_date: ISODate('2023-05-08T12:15:00Z')
  },
  {
    sender_id: 2,
    chat_id: 3,
    content: 'Can someone help me with this issue I am having?',
    created_date: ISODate('2023-05-09T13:20:00Z')
  },
  {
    sender_id: 5,
    chat_id: 3,
    content: 'Sure, what do you need help with?',
    created_date: ISODate('2023-05-10T15:30:00Z')
  },
  {
    sender_id: 8,
    chat_id: 3,
    content: 'I might be able to assist as well.',
    created_date: ISODate('2023-05-11T16:45:00Z')
  },
  {
    sender_id: 9,
    chat_id: 3,
    content: 'I am not sure, but I can take a look.',
    created_date: ISODate('2023-05-12T09:10:00Z')
  },
  {
    sender_id: 10,
    chat_id: 3,
    content: 'Let me know if you need any further assistance.',
    created_date: ISODate('2023-05-13T11:20:00Z')
  }
]);