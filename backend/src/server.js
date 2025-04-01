require('dotenv').config();
const app = require('./app');

const PORT = process.env.PORT || 5000;

app.get('/', (req, res) => res.status(200).json({ message: 'SUCCESSFUL' }));

app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
