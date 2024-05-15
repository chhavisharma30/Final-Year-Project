from flask import Flask, request, jsonify
import pickle
import joblib
import pandas as pd

# remedy = pickle.load(open('remedy.pkl', 'rb'))
remedy = joblib.load('remedy.joblib')
app = Flask(__name__)


@app.route('/')
def home():
    return "Hello World"


@app.route('/remedy', methods=['POST'])
def remedy():
    user_input = request.form.get('user_input')

    # Split the input into individual symptoms and map to features
    user_symptoms = [symptom.strip().lower() for symptom in user_input.split(',')]

    # Initialize a dictionary to store the user's symptoms mapped to features
    user_symptoms_dict = {symptom: 1 if symptom in user_symptoms else 0 for symptom in user_symptoms}

    # Convert user input to a DataFrame (matching the model's expectations)
    user_data = pd.DataFrame([user_symptoms_dict])

    # Use the best classifier to predict the disease based on user symptoms
    predicted_disease = remedy.predict(user_data)
    print(predicted_disease)

    return jsonify({'predicted_disease': predicted_disease})


if __name__ == '__main__':
    app.run(debug=True)
